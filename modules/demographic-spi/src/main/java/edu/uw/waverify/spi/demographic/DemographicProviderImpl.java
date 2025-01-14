package edu.uw.waverify.spi.demographic;

import java.util.Map;

import org.keycloak.models.KeycloakSession;

/**
 * Implementation of the DemographicProvider interface. Handles the core demographic validation logic.
 */
public
class DemographicProviderImpl implements DemographicProvider {

	private final KeycloakSession                session;
	private final DemographicVerificationService verificationService;

	/**
	 * Constructor to initialize the Keycloak session.
	 *
	 * @param session
	 * 		the KeycloakSession instance.
	 */
	public
	DemographicProviderImpl( KeycloakSession session, DemographicVerificationService verificationService ) {

		this.session = session;
		this.verificationService = verificationService;
	}

	@Override
	public
	void close( ) {

	}

	/**
	 * Validates demographic information for a user.
	 *
	 * @param userId
	 * 		the unique identifier of the user.
	 * @param demographics
	 * 		a map of demographic attributes (e.g., firstName, lastName, dob).
	 *
	 * @return true if the demographics are valid; false otherwise.
	 */
	@Override
	public
	boolean validateDemographics( String userId, Map< String, String > demographics ) {

		if ( demographics == null || demographics.isEmpty( ) ) {
			return false;
		}

		var firstName = demographics.get( "firstName" );
		var lastName  = demographics.get( "lastName" );

		if ( firstName == null || lastName == null ) {
			return false;
		}

		return verificationService.verify( userId, demographics );
	}

}
