package edu.uw.waverify.spi.demographic;

import java.util.Map;

import org.keycloak.provider.Provider;

/**
 * Interface defining the contract for demographic validation providers. Implementations of this interface are
 * responsible for validating user-provided demographic information to ensure accuracy and compliance with
 * application-specific requirements.
 */
public
interface DemographicProvider extends Provider {

	/**
	 * Validates demographic information provided by the user.
	 *
	 * @param userId
	 * 		The unique identifier of the user whose demographics are being validated.
	 * @param demographics
	 * 		A map containing key-value pairs of demographic data to validate. Keys represent demographic attributes (e.g.,
	 * 		"firstName", "email"), and values are the corresponding user-provided data.
	 *
	 * @return {@code true} if the demographic information is valid, {@code false} otherwise.
	 */
	boolean validateDemographics( String userId, Map< String, String > demographics );

}
