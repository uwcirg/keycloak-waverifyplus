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
 * Handles demographic authentication in a Keycloak authentication flow.
 * <p>
 * This authenticator collects user demographic data, verifies it, and creates or updates a PIN credential if provided.
 * </p>
 */
@Setter
@Getter
@JBossLog
public
class DemographicAuthenticatorImpl implements DemographicAuthenticator {

	private final DemographicVerificationService verificationService;

	/**
	 * Constructs the authenticator with a verification service.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param baseUrl
	 * 		the verification service base URL.
	 */
	public
	DemographicAuthenticatorImpl( KeycloakSession session, String baseUrl ) {

		this.verificationService = new DemographicVerificationServiceImpl( session, baseUrl );
	}

	/**
	 * Initiates authentication by displaying the demographic form.
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
	 * Processes the demographic data and validates the user.
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

		var formData = context.getHttpRequest( )
		                      .getDecodedFormParameters( );
		var authorization = formData.getFirst( "authorization" );
		if ( authorization == null || !authorization.equals( "on" ) ) {
			var challenge = context.form( )
			                       .setError( "Consent with the Authorization Declaration is needed to proceed." )
			                       .createForm( "login.ftl" );
			context.failureChallenge( AuthenticationFlowError.GENERIC_AUTHENTICATION_ERROR, challenge );
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
	 * Determines if an authenticated user is required.
	 *
	 * @return {@code false} since authentication is based on demographic input.
	 */
	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	/**
	 * Checks whether this authenticator is configured for a given user.
	 *
	 * @param keycloakSession
	 * 		the Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 *
	 * @return {@code false}, as no specific configuration is required.
	 */
	@Override
	public
	boolean configuredFor( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {

		return false;
	}

	/**
	 * Defines any required actions for the user.
	 *
	 * @param keycloakSession
	 * 		the Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 */
	@Override
	public
	void setRequiredActions( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {
		// No required actions.
	}

	/**
	 * Cleans up any necessary resources.
	 */
	@Override
	public
	void close( ) {
		// No specific cleanup required.
	}

	/**
	 * Stores or updates the user's PIN credential. Ensures the credential is properly initialized with secret data.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param realm
	 * 		the Keycloak realm.
	 * @param user
	 * 		the user model.
	 * @param pin
	 * 		the user's PIN.
	 */
	private
	void storePinCredential( KeycloakSession session, RealmModel realm, UserModel user, String pin ) {

		var provider = ( PinCredentialProvider ) session.getProvider( CredentialProvider.class, PinCredentialProviderFactory.PROVIDER_ID );

		var pinCredentialModel = PinCredentialModel.createPin( pin );

		user.credentialManager( )
		    .getStoredCredentialsByTypeStream( provider.getType( ) )
		    .findFirst( )
		    .ifPresentOrElse( existingCredential -> {
			    log.warn( "Updating existing PIN credential for user: " + user.getUsername( ) );
			    provider.updateCredential( realm, user, pinCredentialModel );
		    }, ( ) -> {
			    log.warn( "Creating new PIN credential for user: " + user.getUsername( ) );
			    provider.createCredential( realm, user, pinCredentialModel );
		    } );
	}

}
