package edu.uw.waverify.demographic.authenticator;

import java.util.Map;

import org.keycloak.authentication.Authenticator;

import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationService;

/**
 * Defines the contract for demographic authentication providers.
 * <p>
 * Implementations of this interface are responsible for validating user-provided demographic information to ensure
 * accuracy and compliance with application-specific requirements.
 * </p>
 */
public
interface DemographicAuthenticator extends Authenticator {

	/**
	 * Retrieves the demographic verification service used for validation.
	 *
	 * @return an instance of {@link DemographicVerificationService}.
	 */
	DemographicVerificationService getVerificationService( );

	/**
	 * Validates demographic information provided by the user.
	 *
	 * @param demographics
	 * 		a map containing key-value pairs of demographic data to validate. Keys represent demographic attributes (e.g.,
	 * 		"firstName", "email"), and values are the corresponding user-provided data.
	 *
	 * @return {@code true} if the demographic information is valid, otherwise {@code false}.
	 */
	boolean validateDemographics( Map< String, String > demographics );

}
