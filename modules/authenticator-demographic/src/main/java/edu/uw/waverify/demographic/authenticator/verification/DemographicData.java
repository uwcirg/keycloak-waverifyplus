package edu.uw.waverify.demographic.authenticator.verification;

import lombok.Data;

/**
 * Represents demographic information provided by the user. This class encapsulates personal details such as name, date
 * of birth, email, and phone number.
 */
@Data
public
class DemographicData {

	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String email;
	private String phoneNumber;

	/**
	 * Creates an empty instance of {@code DemographicData}.
	 */
	public
	DemographicData( ) {

	}

	/**
	 * Creates an instance of {@code DemographicData} with all fields initialized.
	 *
	 * @param firstName
	 * 		User's first name.
	 * @param lastName
	 * 		User's last name.
	 * @param dateOfBirth
	 * 		User's date of birth in YYYY-MM-DD format.
	 * @param email
	 * 		User's email address.
	 * @param phoneNumber
	 * 		User's phone number.
	 */
	public
	DemographicData( String firstName, String lastName, String dateOfBirth, String email, String phoneNumber ) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Returns a string representation of this {@code DemographicData} instance.
	 *
	 * @return A formatted string containing field values.
	 */
	@Override
	public
	String toString( ) {

		return "DemographicData{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", dateOfBirth='" + dateOfBirth + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
	}

}
