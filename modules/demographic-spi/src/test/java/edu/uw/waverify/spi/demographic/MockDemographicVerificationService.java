package edu.uw.waverify.spi.demographic;

import java.util.Map;

import edu.uw.waverify.spi.demographic.verification.DemographicVerificationService;

/**
 * Mock implementation of {@link DemographicVerificationService} for testing purposes. This class simulates the behavior
 * of demographic verification logic.
 */
public
class MockDemographicVerificationService implements DemographicVerificationService {

	/**
	 * Verifies if the provided demographic data contains both "firstName" and "lastName" keys.
	 *
	 * @param userId
	 * 		the user ID to validate demographics for.
	 * @param demographics
	 * 		a map containing demographic data.
	 *
	 * @return true if the demographic data contains "firstName" and "lastName", false otherwise.
	 */
	@Override
	public
	boolean verify( String userId, Map< String, String > demographics ) {

		return demographics.containsKey( "firstName" ) && demographics.containsKey( "lastName" );
	}

}
