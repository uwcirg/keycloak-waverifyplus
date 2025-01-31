package edu.uw.waverify.demographic.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.*;

import edu.uw.waverify.demographic.authenticator.verification.*;
import edu.uw.waverify.demographic.identification.EmailLoginLinkGenerator;
import edu.uw.waverify.pin.PinCredentialProvider;
import edu.uw.waverify.pin.PinCredentialProviderFactory;
import edu.uw.waverify.pin.credential.PinCredentialModel;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;

/**
 * Implementation of the {@link DemographicAuthenticator} interface.
 * <p>
 * This class handles demographic authentication within a Keycloak authentication flow. It collects user demographic
 * data, validates it using a verification service, and determines whether authentication should proceed.
 * </p>
 */
@Setter
@Getter
@JBossLog
public
class DemographicAuthenticatorImpl implements DemographicAuthenticator {

	private final DemographicVerificationService verificationService;

	/**
	 * Constructs a {@code DemographicAuthenticatorImpl} instance with a Keycloak session and a demographic verification
	 * service.
	 *
	 * @param session
	 * 		the Keycloak session used for authentication flow interactions.
	 * @param baseUrl
	 * 		the base URL for the demographic verification service.
	 */
	public
	DemographicAuthenticatorImpl( KeycloakSession session, String baseUrl ) {

		this.verificationService = new DemographicVerificationServiceImpl( session, baseUrl );
	}

	/**
	 * Initiates the authentication process by displaying a demographic information form.
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		context.form( )
		       .setAttribute( "demographicRequired", true );
		var challenge = context.form( )
		                       .createForm( "login.ftl" );
		context.challenge( challenge );
	}

	/**
	 * Handles the user's submitted demographic information and performs validation.
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
	@Override
	public
	void action( AuthenticationFlowContext context ) {

		var demographicData = DemographicDataHelper.extractFromRequest( context.getHttpRequest( ) );

		if ( !DemographicDataHelper.isValid( demographicData ) ) {
			var challenge = context.form( )
			                       .setError( "Demographic validation failed. Please check your details." )
			                       .createForm( "login.ftl" );
			context.failureChallenge( AuthenticationFlowError.INVALID_CREDENTIALS, challenge );
			return;
		}

		var authSession = context.getAuthenticationSession( );
		DemographicDataHelper.storeInAuthSession( authSession, demographicData );

		var user = DemographicDataHelper.saveUser( context.getSession( ), context.getRealm( ), authSession );
		context.setUser( user );

		if ( demographicData.getPin( ) != null ) {
			storePinCredential( context.getSession( ), context.getRealm( ), user, demographicData.getPin( ) );
		}

		EmailLoginLinkGenerator.sendLoginEmail( context.getSession( ), user );
		context.success( );
	}

	/**
	 * Indicates whether a user is required for this authentication process.
	 *
	 * @return {@code false} since demographic validation does not require an authenticated user.
	 */
	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	/**
	 * Determines whether this authenticator is configured for a given user in a realm.
	 *
	 * @param keycloakSession
	 * 		the current Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 *
	 * @return {@code false} since this authenticator does not require specific user configuration.
	 */
	@Override
	public
	boolean configuredFor( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {

		return false;
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

	/**
	 * Cleans up resources if needed.
	 */
	@Override
	public
	void close( ) {
		// No specific cleanup required.
	}

	private
	void storePinCredential( KeycloakSession session, RealmModel realm, UserModel user, String pin ) {

		var provider = ( PinCredentialProvider ) session.getProvider( CredentialProvider.class, PinCredentialProviderFactory.PROVIDER_ID );

		if ( provider.isConfiguredFor( realm, user, provider.getType( ) ) ) {
			provider.updateCredential( realm, user, new PinCredentialModel( pin ) );
		} else {
			provider.createCredential( realm, user, new PinCredentialModel( pin ) );
		}
	}

}
