package edu.uw.waverify.authenticator.demographic.verification;

import java.util.Map;

/**
 * Interface defining the contract for demographic verification services. Implementations of this interface are
 * responsible for validating demographic information provided by users.
 */
public
interface DemographicVerificationService {

	/**
	 * Verifies the provided demographic information for a given user ID.
	 *
	 * @param demographics
	 * 		A map containing demographic data where keys represent the attribute names and values represent their
	 * 		corresponding values.
	 *
	 * @return {@code true} if the demographic information is valid, {@code false} otherwise.
	 */
	boolean verify( Map< String, String > demographics );

}
