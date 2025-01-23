package edu.uw.waverify.spi.demographic;

import java.util.HashMap;
import java.util.Map;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.*;

import edu.uw.waverify.spi.demographic.verification.DemographicVerificationService;

import jakarta.ws.rs.core.Response;

/**
 * Implementation of the DemographicAuthenticator interface. Handles the core demographic validation logic.
 */
public
class DemographicAuthenticatorImpl implements DemographicAuthenticator {

	private final KeycloakSession                session;
	private final DemographicVerificationService verificationService;

	/**
	 * Constructor to initialize the Keycloak session and demographic verification service.
	 *
	 * @param session
	 * 		The KeycloakSession instance.
	 * @param verificationService
	 * 		The service handling demographic verification logic.
	 */
	public
	DemographicAuthenticatorImpl( KeycloakSession session, DemographicVerificationService verificationService ) {

		this.session = session;
		this.verificationService = verificationService;
	}

	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		Response challenge = context.form( )
		                            .createForm( "demographic.ftl" );
		context.challenge( challenge );
		context.success( );
	}

	@Override
	public
	void action( AuthenticationFlowContext context ) {

		boolean validated = validateDemographics( context );
		if ( !validated ) {
			Response challenge = context.form( )
			                            .setError( "badSecret" )
			                            .createForm( "demographic.ftl" );
			context.failureChallenge( AuthenticationFlowError.INVALID_CREDENTIALS, challenge );
			return;
		}
		context.success( );
	}

	@Override
	public
	boolean requiresUser( ) {

		return true;
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
		// No specific cleanup logic is needed for this implementation.
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
