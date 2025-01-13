package edu.uw.waverify.spi.demographic;

import java.util.Map;

import org.keycloak.provider.Provider;

/**
 * Interface defining the contract for demographic validation providers.
 */
public
interface DemographicProvider extends Provider {

	/**
	 * Validates demographic information provided by the user.
	 *
	 * @param userId
	 * 		the user ID to validate demographics for.
	 * @param demographics
	 * 		JSON-like map containing demographic data.
	 *
	 * @return true if valid, false otherwise.
	 */
	boolean validateDemographics( String userId, Map< String, String > demographics );

}
