package edu.uw.waverify.demographic.authenticator;

import java.util.HashMap;
import java.util.Map;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.*;

import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationService;
import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationServiceImpl;

import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of the {@link DemographicAuthenticator} interface.
 * <p>
 * This class handles demographic authentication within a Keycloak authentication flow. It prompts users for demographic
 * information, validates it using a verification service, and determines whether authentication should proceed.
 * </p>
 */
@Setter
@Getter
public
class DemographicAuthenticatorImpl implements DemographicAuthenticator {

	private DemographicVerificationService verificationService;

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

		verificationService = new DemographicVerificationServiceImpl( session, baseUrl );
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
		                       .createForm( "demographic.ftl" );
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

		var validated = validateDemographics( context );
		if ( !validated ) {
			var challenge = context.form( )
			                       .setError( "Demographic validation failed. Please check your details." )
			                       .createForm( "demographic.ftl" );
			context.failureChallenge( AuthenticationFlowError.INVALID_CREDENTIALS, challenge );
			return;
		}
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
	 * Cleans up resources by nullifying the verification service reference.
	 */
	@Override
	public
	void close( ) {

		verificationService = null;
	}

	/**
	 * Validates the demographic information provided by the user.
	 *
	 * @param demographics
	 * 		a map containing demographic attributes (e.g., firstName, lastName, dateOfBirth).
	 *
	 * @return {@code true} if the demographics are valid, {@code false} otherwise.
	 */
	@Override
	public
	boolean validateDemographics( Map< String, String > demographics ) {

		if ( demographics == null || demographics.isEmpty( ) ) {
			return false;
		}

		var firstName = demographics.get( "firstName" );
		var lastName  = demographics.get( "lastName" );

		if ( firstName == null || lastName == null ) {
			return false;
		}

		return verificationService.verify( demographics );
	}

	/**
	 * Extracts and validates demographic information from the authentication flow context.
	 *
	 * @param context
	 * 		the authentication flow context.
	 *
	 * @return {@code true} if the demographics are valid, {@code false} otherwise.
	 */
	private
	boolean validateDemographics( AuthenticationFlowContext context ) {

		var formData = context.getHttpRequest( )
		                      .getDecodedFormParameters( );
		var demographics = new HashMap< String, String >( ) {{
			put( "firstName", formData.getFirst( "firstName" ) );
			put( "lastName", formData.getFirst( "lastName" ) );
		}};

		return validateDemographics( demographics );
	}

}
