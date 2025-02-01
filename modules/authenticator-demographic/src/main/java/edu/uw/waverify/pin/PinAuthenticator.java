package edu.uw.waverify.pin;

import java.net.URI;

import org.keycloak.authentication.*;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.*;

import edu.uw.waverify.pin.credential.PinCredentialModel;

import jakarta.ws.rs.core.*;
import lombok.extern.jbosslog.JBossLog;

@JBossLog
public
class PinAuthenticator implements Authenticator, CredentialValidator< PinCredentialProvider > {

	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		if ( context.getUser( ) == null ) {
			log.warn( "No user found" );
			context.failure( AuthenticationFlowError.UNKNOWN_USER );
			return;
		}

		//		if ( hasCookie( context ) ) {
		//			log.warn( "Cookie already exists" );
		//			context.success( );
		//			return;
		//		}

		context.form( )
		       .setAttribute( "pinRequired", true );
		context.form( )
		       .setAttribute( "usernameHidden", true );
		context.form( )
		       .setAttribute( "demographicRequired", false );

		var challenge = context.form( )
		                       .createForm( "login.ftl" );

		context.challenge( challenge );
	}

	@Override
	public
	void action( AuthenticationFlowContext context ) {

		if ( context.getUser( ) == null ) {
			log.warn( "No user found" );
			context.failure( AuthenticationFlowError.UNKNOWN_USER );
			return;
		}

		boolean validated = validateAnswer( context );
		if ( !validated ) {
			log.warn( "PIN not validated" );
			Response challenge = context.form( )
			                            .setError( "badSecret" )
			                            .createForm( "login.ftl" );
			context.failureChallenge( AuthenticationFlowError.INVALID_CREDENTIALS, challenge );
			return;
		}

		log.warn( "PIN validated" );
		setCookie( context );
		context.success( );
	}

	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return getCredentialProvider( session ).isConfiguredFor( realm, user, PinCredentialModel.TYPE );
	}

	/**
	 * Sets required actions for a user, if applicable.
	 *
	 * @param keycloakSession
	 * 		the current Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 */
	@Override
	public
	void setRequiredActions( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {
		// No required actions for this authenticator
	}

	@Override
	public
	void close( ) {
		// No cleanup required
	}

	@Override
	public
	PinCredentialProvider getCredentialProvider( KeycloakSession session ) {

		return ( PinCredentialProvider ) session.getProvider( CredentialProvider.class, PinCredentialProviderFactory.PROVIDER_ID );
	}

	private
	boolean hasCookie( AuthenticationFlowContext context ) {

		Cookie cookie = context.getHttpRequest( )
		                       .getHttpHeaders( )
		                       .getCookies( )
		                       .get( "PIN_ANSWERED" );
		boolean result = cookie != null;
		if ( result ) {
			log.warn( "Bypassing PIN because cookie is set" );
		}
		return result;
	}

	private
	void setCookie( AuthenticationFlowContext context ) {

		AuthenticatorConfigModel config       = context.getAuthenticatorConfig( );
		int                      maxCookieAge = 60 * 60 * 24 * 30; // Default 30 days

		if ( config != null ) {
			maxCookieAge = Integer.parseInt( config.getConfig( )
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

	private
	boolean validateAnswer( AuthenticationFlowContext context ) {

		var session = context.getSession( );
		var formData = context.getHttpRequest( )
		                      .getDecodedFormParameters( );

		String pin = formData.getFirst( "pin" );
		if ( pin == null || pin.isEmpty( ) ) {
			log.warn( "PIN input is missing" );
			return false;
		}

		var provider   = getCredentialProvider( session );
		var credential = provider.getDefaultCredential( session, context.getRealm( ), context.getUser( ) );

		if ( credential == null ) {
			log.warn( "No PIN credential found for user: " + context.getUser( )
			                                                        .getUsername( ) );
			return false;
		}

		String credentialId = credential.getId( );
		if ( credentialId == null || credentialId.isEmpty( ) ) {
			log.warn( "PIN credential ID is missing" );
			return false;
		}

		UserCredentialModel input = new UserCredentialModel( credentialId, getType( session ), pin );
		return provider.isValid( context.getRealm( ), context.getUser( ), input );
	}

}
