package edu.uw.waverify.pin;

import org.keycloak.authentication.*;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.*;

import edu.uw.waverify.SimpleAuthenticator;
import edu.uw.waverify.pin.credential.PinCredentialModel;

import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;

import static org.keycloak.authentication.AuthenticationFlowError.*;

@JBossLog
public
class PinAuthenticator extends SimpleAuthenticator implements Authenticator, CredentialValidator< PinCredentialProvider > {

	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		if ( context.getUser( ) == null ) {
			log.warn( "No user found" );
			context.failure( UNKNOWN_USER );
			return;
		}

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
			context.failure( UNKNOWN_USER );
			return;
		}

		boolean validated = validateAnswer( context );
		if ( !validated ) {
			context.form( )
			       .setAttribute( "pinRequired", true );
			context.form( )
			       .setAttribute( "usernameHidden", true );
			context.form( )
			       .setAttribute( "demographicRequired", false );
			Response challenge = context.form( )
			                            .setError( "badSecret" )
			                            .createForm( "login.ftl" );
			context.failureChallenge( INVALID_CREDENTIALS, challenge );
			return;
		}

		log.warn( "PIN validated" );
		context.success( );
	}

	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return getCredentialProvider( session ).isConfiguredFor( realm, user, PinCredentialModel.TYPE );
	}

	@Override
	public
	PinCredentialProvider getCredentialProvider( KeycloakSession session ) {

		return ( PinCredentialProvider ) session.getProvider( CredentialProvider.class, PinCredentialProviderFactory.PROVIDER_ID );
	}

	@Override
	public
	boolean requiresUser( ) {

		return true;
	}

	private
	boolean validateAnswer( AuthenticationFlowContext context ) {

		var session = context.getSession( );
		var formData = context.getHttpRequest( )
		                      .getDecodedFormParameters( );

		var pin = formData.getFirst( "pin" );
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

		var credentialId = credential.getId( );
		if ( credentialId == null || credentialId.isEmpty( ) ) {
			log.warn( "PIN credential ID is missing" );
			return false;
		}

		var input = new UserCredentialModel( credentialId, getType( session ), pin );
		return provider.isValid( context.getRealm( ), context.getUser( ), input );
	}

}
