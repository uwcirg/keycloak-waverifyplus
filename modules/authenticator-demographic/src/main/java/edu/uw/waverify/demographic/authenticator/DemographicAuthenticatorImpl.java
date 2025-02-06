package edu.uw.waverify.demographic.authenticator;

import java.util.Map;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.*;

import edu.uw.waverify.SimpleAuthenticator;
import edu.uw.waverify.demographic.authenticator.verification.*;
import edu.uw.waverify.demographic.identification.EmailLoginLinkGenerator;
import edu.uw.waverify.pin.PinCredentialProvider;
import edu.uw.waverify.pin.PinCredentialProviderFactory;
import edu.uw.waverify.pin.credential.PinCredentialModel;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;

import static org.keycloak.authentication.AuthenticationFlowError.*;

/**
 * Authenticator for demographic-based user validation in a Keycloak authentication flow.
 * <p>
 * This authenticator collects demographic data, verifies it against an external service, and registers users with
 * optional PIN credential storage.
 * </p>
 */
@Setter
@Getter
@JBossLog
public
class DemographicAuthenticatorImpl extends SimpleAuthenticator implements DemographicAuthenticator {

	private DemographicVerificationService verificationService;

	/**
	 * Constructs a demographic authenticator.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param baseUrl
	 * 		the base URL of the demographic verification service.
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
	 * Processes the demographic data, verifies it, and completes authentication if valid.
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
			                       .setAttribute( "demographicRequired", true )
			                       .setError( "Demographic validation failed. Please check your details." )
			                       .createForm( "login.ftl" );
			context.failureChallenge( INVALID_CREDENTIALS, challenge );
			return;
		}

		var formData      = context.getHttpRequest( )
		                           .getDecodedFormParameters( );
		var authorization = formData.getFirst( "authorization" );
		if ( authorization == null || !authorization.equals( "on" ) ) {
			var challenge = context.form( )
			                       .setAttribute( "demographicRequired", true )
			                       .setError( "Consent with the Authorization Declaration is needed to proceed." )
			                       .createForm( "login.ftl" );
			context.failureChallenge( GENERIC_AUTHENTICATION_ERROR, challenge );
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
	 * Checks if this authenticator is configured for a user.
	 *
	 * @param keycloakSession
	 * 		the Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 *
	 * @return {@code false}, as no specific user configuration is required.
	 */
	@Override
	public
	boolean configuredFor( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {

		return false;
	}

	/**
	 * Validates demographic data using the verification service.
	 *
	 * @param demographics
	 * 		a map containing key-value pairs of demographic attributes.
	 *
	 * @return {@code true} if the demographic data is valid, {@code false} otherwise.
	 */
	@Override
	public
	boolean validateDemographics( Map< String, String > demographics ) {

		return verificationService.verify( demographics );
	}

	/**
	 * Stores or updates the user's PIN credential.
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

		user.credentialManager( )
		    .getStoredCredentialsByTypeStream( provider.getType( ) )
		    .findFirst( )
		    .ifPresentOrElse( existingCredential -> {
			    var updatedModel = PinCredentialModel.createPin( pin, existingCredential.getId( ) );
			    provider.updateCredential( user, updatedModel, pin );
		    }, ( ) -> {
			    var pinCredentialModel = PinCredentialModel.createPin( pin );
			    provider.createCredential( realm, user, pinCredentialModel );
		    } );
	}

}
