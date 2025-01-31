package edu.uw.waverify.pin;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.keycloak.authentication.*;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.*;

import jakarta.ws.rs.core.*;

public
class PinAuthenticator implements Authenticator, CredentialValidator< PinCredentialProvider > {

	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		if ( hasCookie( context ) ) {
			context.success( );
			return;
		}
		Response challenge = context.form( )
		                            .createForm( "pin.ftl" );
		context.challenge( challenge );
	}

	@Override
	public
	void action( AuthenticationFlowContext context ) {

		boolean validated = validateAnswer( context );
		if ( !validated ) {
			Response challenge = context.form( )
			                            .setError( "badSecret" )
			                            .createForm( "pin.ftl" );
			context.failureChallenge( AuthenticationFlowError.INVALID_CREDENTIALS, challenge );
			return;
		}
		setCookie( context );
		context.success( );
	}

	@Override
	public
	boolean requiresUser( ) {

		return true;
	}

	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return getCredentialProvider( session ).isConfiguredFor( realm, user, getType( session ) );
	}

	@Override
	public
	void setRequiredActions( KeycloakSession session, RealmModel realm, UserModel user ) {

		user.addRequiredAction( PinRequiredAction.PROVIDER_ID );
	}

	@Override
	public
	List< RequiredActionFactory > getRequiredActions( KeycloakSession session ) {

		return Collections.singletonList( ( PinRequiredActionFactory ) session.getKeycloakSessionFactory( )
		                                                                      .getProviderFactory( RequiredActionProvider.class, PinRequiredAction.PROVIDER_ID ) );
	}

	@Override
	public
	void close( ) {

	}

	@Override
	public
	PinCredentialProvider getCredentialProvider( KeycloakSession session ) {

		return ( PinCredentialProvider ) session.getProvider( CredentialProvider.class, PinCredentialProviderFactory.PROVIDER_ID );
	}

	protected
	boolean hasCookie( AuthenticationFlowContext context ) {

		Cookie cookie = context.getHttpRequest( )
		                       .getHttpHeaders( )
		                       .getCookies( )
		                       .get( "PIN_ANSWERED" );
		boolean result = cookie != null;
		if ( result ) {
			System.out.println( "Bypassing pin because cookie is set" );
		}
		return result;
	}

	protected
	void setCookie( AuthenticationFlowContext context ) {

		AuthenticatorConfigModel config       = context.getAuthenticatorConfig( );
		int                      maxCookieAge = 60 * 60 * 24 * 30; // 30 days
		if ( config != null ) {
			maxCookieAge = Integer.valueOf( config.getConfig( )
			                                      .get( "cookie.max.age" ) );

		}
		URI uri = context.getUriInfo( )
		                 .getBaseUriBuilder( )
		                 .path( "realms" )
		                 .path( context.getRealm( )
		                               .getName( ) )
		                 .build( );

		NewCookie newCookie = new NewCookie.Builder( "PIN_ANSWERED" ).value( "true" )
		                                                             .path( uri.getRawPath( ) )
		                                                             .maxAge( maxCookieAge )
		                                                             .secure( false )
		                                                             .build( );
		context.getSession( )
		       .getContext( )
		       .getHttpResponse( )
		       .setCookieIfAbsent( newCookie );
	}

	protected
	boolean validateAnswer( AuthenticationFlowContext context ) {

		MultivaluedMap< String, String > formData = context.getHttpRequest( )
		                                                   .getDecodedFormParameters( );
		String secret       = formData.getFirst( "pin" );
		String credentialId = formData.getFirst( "credentialId" );
		if ( credentialId == null || credentialId.isEmpty( ) ) {
			credentialId = getCredentialProvider( context.getSession( ) ).getDefaultCredential( context.getSession( ), context.getRealm( ), context.getUser( ) )
			                                                             .getId( );
		}

		UserCredentialModel input = new UserCredentialModel( credentialId, getType( context.getSession( ) ), secret );
		return getCredentialProvider( context.getSession( ) ).isValid( context.getRealm( ), context.getUser( ), input );
	}

}
