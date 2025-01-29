package edu.uw.waverify.demographic.authenticator;

import java.util.HashMap;
import java.util.Map;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.*;

import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationService;
import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationServiceImpl;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of the DemographicAuthenticator interface. Handles the core demographic validation logic.
 */
@Setter
@Getter
public
class DemographicAuthenticatorImpl implements DemographicAuthenticator {

	private DemographicVerificationService verificationService;

	/**
	 * Constructor to initialize the Keycloak session and demographic verification service.
	 *
	 * @param session
	 * 		The KeycloakSession instance.
	 * @param baseUrl
	 * 		URL for the service handling demographic verification logic.
	 */
	public
	DemographicAuthenticatorImpl( KeycloakSession session, String baseUrl ) {

		verificationService = new DemographicVerificationServiceImpl( session, baseUrl );

	}

	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		context.form( )
		       .setAttribute( "demographicRequired", true );
		Response challenge = context.form( )
		                            .createForm( "demographic.ftl" );
		context.challenge( challenge );
	}

	@Override
	public
	void action( AuthenticationFlowContext context ) {

		boolean validated = validateDemographics( context );
		if ( !validated ) {
			Response challenge = context.form( )
			                            .setError( "Demographic validation failed. Please check your details." )
			                            .createForm( "demographic.ftl" );
			context.failureChallenge( AuthenticationFlowError.INVALID_CREDENTIALS, challenge );
			return;
		}
		context.success( );
	}

	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	@Override
	public
	boolean configuredFor( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {

		return false;
	}

	@Override
	public
	void setRequiredActions( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {

	}

	/**
	 * Cleans up resources or performs necessary teardown operations.
	 */
	@Override
	public
	void close( ) {

		verificationService = null;
	}

	/**
	 * Validates demographic information for a user.
	 *
	 * @param demographics
	 * 		A map of demographic attributes (e.g., firstName, lastName, dob).
	 *
	 * @return {@code true} if the demographics are valid; {@code false} otherwise.
	 */
	@Override
	public
	boolean validateDemographics( Map< String, String > demographics ) {

		if ( demographics == null || demographics.isEmpty( ) ) {
			return false;
		}

		String firstName = demographics.get( "firstName" );
		String lastName  = demographics.get( "lastName" );

		if ( firstName == null || lastName == null ) {
			return false;
		}

		return verificationService.verify( demographics );
	}

	private
	boolean validateDemographics( AuthenticationFlowContext context ) {

		var formData = context.getHttpRequest( )
		                      .getDecodedFormParameters( );
		var demographics = new HashMap< String, String >( ) {
			{
				put( "firstName", formData.getFirst( "firstName" ) );
				put( "lastName", formData.getFirst( "lastName" ) );
			}
		};

		return validateDemographics( demographics );
	}

}
