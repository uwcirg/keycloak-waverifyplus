package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

/**
 * Interface defining the contract for demographic verification services.
 * <p>
 * Implementations of this interface are responsible for validating demographic information provided by users, ensuring
 * that it meets the required criteria for authentication or registration.
 * </p>
 */
public
interface DemographicVerificationService {

	/**
	 * Verifies the provided demographic information.
	 *
	 * @param demographics
	 * 		a map containing demographic data where keys represent attribute names and values represent their corresponding
	 * 		values.
	 *
	 * @return {@code true} if the demographic information is valid, otherwise {@code false}.
	 */
	boolean verify( Map< String, String > demographics );

}
