package edu.uw.waverify.authenticator.demographic.verification;

import lombok.Data;

/**
 * A data class representing demographic information provided by the user. This class encapsulates personal information
 * such as name, date of birth, email, and phone number.
 */
@Data
public
class DemographicData {

	private String firstName;
	private String lastName;
	private String dateOfBirth; // Format: YYYY-MM-DD
	private String email;
	private String phoneNumber;

	/**
	 * Default constructor for creating an empty instance of {@code DemographicData}.
	 */
	public
	DemographicData( ) {

	}

	/**
	 * Constructor for initializing all fields of {@code DemographicData}.
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
	 * Converts this {@code DemographicData} instance to a string representation.
	 *
	 * @return A string containing the field values.
	 */
	@Override
	public
	String toString( ) {

		return "DemographicData{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", dateOfBirth='" + dateOfBirth + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
	}

}
