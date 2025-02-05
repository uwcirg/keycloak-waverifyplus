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
 * Handles demographic authentication in a Keycloak authentication flow.
 * <p>
 * This authenticator collects user demographic data, verifies it, and creates or updates a PIN credential if provided.
 * </p>
 */
@Setter
@Getter
@JBossLog
public
class DemographicAuthenticatorImpl extends SimpleAuthenticator implements DemographicAuthenticator {

	private DemographicVerificationService verificationService;

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
			context.form( )
			       .setAttribute( "demographicRequired", true );
			var challenge = context.form( )
			                       .setError( "Demographic validation failed. Please check your details." )
			                       .createForm( "login.ftl" );
			context.failureChallenge( INVALID_CREDENTIALS, challenge );
			return;
		}

		var formData = context.getHttpRequest( )
		                      .getDecodedFormParameters( );
		var authorization = formData.getFirst( "authorization" );
		if ( authorization == null || !authorization.equals( "on" ) ) {
			context.form( )
			       .setAttribute( "demographicRequired", true );
			var challenge = context.form( )
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

	@Override
	public
	boolean validateDemographics( Map< String, String > demographics ) {

		return verificationService.verify( demographics );
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
			    provider.updateCredential( realm, user, pinCredentialModel );
		    }, ( ) -> {
			    provider.createCredential( realm, user, pinCredentialModel );
		    } );
	}

}
