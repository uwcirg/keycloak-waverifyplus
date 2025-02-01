package edu.uw.waverify.pin;

import org.keycloak.authentication.*;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.sessions.AuthenticationSessionModel;

import edu.uw.waverify.pin.credential.PinCredentialModel;

import jakarta.ws.rs.core.Response;

import static edu.uw.waverify.pin.credential.PinCredentialModel.TYPE;

/**
 * Required action provider for configuring a PIN credential.
 * <p>
 * This class is responsible for handling the required action where a user is prompted to configure their PIN
 * credential.
 * </p>
 */
public
class PinRequiredAction implements RequiredActionProvider, CredentialRegistrator {

	/**
	 * The provider ID for the PIN configuration action.
	 */
	public static final String PROVIDER_ID = "pin_config";

	/**
	 * Closes any resources used by this provider.
	 */
	@Override
	public
	void close( ) {

	}

	/**
	 * Evaluates whether this required action should be triggered for the user.
	 *
	 * @param context
	 * 		the required action context.
	 */
	@Override
	public
	void evaluateTriggers( RequiredActionContext context ) {

	}

	/**
	 * Presents the user with a form to configure their PIN.
	 *
	 * @param context
	 * 		the required action context.
	 */
	@Override
	public
	void requiredActionChallenge( RequiredActionContext context ) {

		Response challenge = context.form( )
		                            .createForm( "pin-config.ftl" );
		context.challenge( challenge );
	}

	/**
	 * Processes the user input from the PIN configuration form and stores the credential.
	 *
	 * @param context
	 * 		the required action context.
	 */
	@Override
	public
	void processAction( RequiredActionContext context ) {

		var pin = context.getHttpRequest( )
		                 .getDecodedFormParameters( )
		                 .getFirst( "pin" );

		var provider = ( PinCredentialProvider ) context.getSession( )
		                                                .getProvider( CredentialProvider.class, TYPE );

		provider.createCredential( context.getRealm( ), context.getUser( ), PinCredentialModel.createPin( pin ) );
		context.success( );
	}

	/**
	 * Retrieves the credential type associated with this required action.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param authenticationSession
	 * 		the authentication session model.
	 *
	 * @return the credential type ("PIN").
	 */
	@Override
	public
	String getCredentialType( KeycloakSession session, AuthenticationSessionModel authenticationSession ) {

		return TYPE;
	}

}
