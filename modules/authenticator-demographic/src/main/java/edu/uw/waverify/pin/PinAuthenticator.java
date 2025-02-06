package edu.uw.waverify.pin;

import org.keycloak.authentication.*;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.*;

import edu.uw.waverify.SimpleAuthenticator;
import edu.uw.waverify.pin.credential.PinCredentialModel;

import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;

import static org.keycloak.authentication.AuthenticationFlowError.*;

/**
 * Authenticator for validating a user's PIN credential.
 * <p>
 * This authenticator challenges the user to enter a PIN and verifies it against stored credentials.
 * </p>
 */
@JBossLog
public
class PinAuthenticator extends SimpleAuthenticator implements Authenticator, CredentialValidator< PinCredentialProvider > {

	/**
	 * Initiates the authentication challenge by prompting the user for a PIN.
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
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

	/**
	 * Handles user input and validates the provided PIN.
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
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

	/**
	 * Checks if the authenticator is configured for a given user.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param realm
	 * 		the realm.
	 * @param user
	 * 		the user.
	 *
	 * @return {@code true} if the user has a configured PIN credential.
	 */
	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return getCredentialProvider( session ).isConfiguredFor( realm, user, PinCredentialModel.TYPE );
	}

	/**
	 * Retrieves the credential provider responsible for PIN authentication.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return the {@link PinCredentialProvider} instance.
	 */
	@Override
	public
	PinCredentialProvider getCredentialProvider( KeycloakSession session ) {

		return ( PinCredentialProvider ) session.getProvider( CredentialProvider.class, PinCredentialProviderFactory.PROVIDER_ID );
	}

	/**
	 * Indicates whether a user is required for this authenticator.
	 *
	 * @return {@code true}, as a user must be identified before verifying the PIN.
	 */
	@Override
	public
	boolean requiresUser( ) {

		return true;
	}

	/**
	 * Validates the user's provided PIN.
	 *
	 * @param context
	 * 		the authentication flow context.
	 *
	 * @return {@code true} if the PIN is valid, otherwise {@code false}.
	 */
	private
	boolean validateAnswer( AuthenticationFlowContext context ) {

		var session  = context.getSession( );
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
