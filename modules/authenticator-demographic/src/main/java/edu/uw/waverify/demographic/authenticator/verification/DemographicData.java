package edu.uw.waverify.demographic.authenticator.verification;

import lombok.*;

/**
 * Represents demographic information provided by the user.
 * <p>
 * This class encapsulates personal details such as name, date of birth, email, and PIN.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public
class DemographicData {

	/**
	 * The first name of the user.
	 */
	private String firstName;

	/**
	 * The last name of the user.
	 */
	private String lastName;

	/**
	 * The date of birth of the user.
	 */
	private String dateOfBirth;

	/**
	 * The email address of the user.
	 */
	private String email;

	/**
	 * The PIN associated with the user.
	 */
	private String pin;

}
