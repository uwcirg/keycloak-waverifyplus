package edu.uw.waverify.spi.demographic;

import java.util.Map;

import org.keycloak.models.KeycloakSession;

/**
 * Implementation of the DemographicProvider interface. Handles the core demographic validation logic.
 */
public
class DemographicProviderImpl implements DemographicProvider {

	private final KeycloakSession session;

	/**
	 * Constructor to initialize the Keycloak session.
	 *
	 * @param session
	 * 		the KeycloakSession instance.
	 */
	public
	DemographicProviderImpl( KeycloakSession session ) {

		this.session = session;
	}

	@Override
	public
	void close( ) {
		// Cleanup resources if necessary
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
		// Check for missing or null userId or demographics
		if ( userId == null || demographics == null || demographics.isEmpty( ) ) {
			return false;
		}

		// Example validation logic: Check required keys
		String firstName = demographics.get( "firstName" );
		String lastName  = demographics.get( "lastName" );
		String dob       = demographics.get( "dob" );

		if ( firstName == null || lastName == null || dob == null ) {
			return false;
		}

		// Simulated validation logic for demonstration
		// Replace with an external API/database check as needed
		return firstName.equalsIgnoreCase( "John" ) && lastName.equalsIgnoreCase( "Doe" ) && dob.equals( "1990-01-01" );
	}

}
