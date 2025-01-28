package edu.uw.waverify.demographic.authenticator;

import java.util.Map;

import org.keycloak.authentication.Authenticator;

import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationService;

/**
 * Interface defining the contract for demographic validation providers. Implementations of this interface are
 * responsible for validating user-provided demographic information to ensure accuracy and compliance with
 * application-specific requirements.
 */
public
interface DemographicAuthenticator extends Authenticator {

	DemographicVerificationService getVerificationService( );

	/**
	 * Validates demographic information provided by the user.
	 *
	 * @param demographics
	 * 		A map containing key-value pairs of demographic data to validate. Keys represent demographic attributes (e.g.,
	 * 		"firstName", "email"), and values are the corresponding user-provided data. Example: {"firstName": "John",
	 * 		"dob": "2000-01-01"}.
	 *
	 * @return {@code true} if the demographic information is valid, {@code false} otherwise.
	 */
	boolean validateDemographics( Map< String, String > demographics );

}
