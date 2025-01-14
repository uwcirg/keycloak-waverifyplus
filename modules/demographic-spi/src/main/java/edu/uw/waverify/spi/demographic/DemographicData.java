package edu.uw.waverify.spi.demographic;

import java.util.Objects;

/**
 * A data class representing demographic information provided by the user. This class encapsulates personal information
 * such as name, date of birth, email, and phone number.
 */
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
	 * Generates a hash code for this {@code DemographicData} instance based on its fields.
	 *
	 * @return The hash code.
	 */
	@Override
	public
	int hashCode( ) {

		return Objects.hash( firstName, lastName, dateOfBirth, email, phoneNumber );
	}

	/**
	 * Checks if this {@code DemographicData} instance is equal to another object.
	 *
	 * @param o
	 * 		The object to compare with.
	 *
	 * @return {@code true} if the objects are equal, otherwise {@code false}.
	 */
	@Override
	public
	boolean equals( Object o ) {

		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass( ) != o.getClass( ) ) {
			return false;
		}
		DemographicData that = ( DemographicData ) o;
		return Objects.equals( firstName, that.firstName ) && Objects.equals( lastName, that.lastName ) && Objects.equals( dateOfBirth, that.dateOfBirth ) && Objects.equals( email, that.email ) && Objects.equals( phoneNumber, that.phoneNumber );
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

	/**
	 * Retrieves the user's date of birth.
	 *
	 * @return The date of birth in YYYY-MM-DD format.
	 */
	public
	String getDateOfBirth( ) {

		return dateOfBirth;
	}

	/**
	 * Retrieves the user's email address.
	 *
	 * @return The email address.
	 */
	public
	String getEmail( ) {

		return email;
	}

	/**
	 * Retrieves the user's first name.
	 *
	 * @return The first name.
	 */
	public
	String getFirstName( ) {

		return firstName;
	}

	/**
	 * Retrieves the user's last name.
	 *
	 * @return The last name.
	 */
	public
	String getLastName( ) {

		return lastName;
	}

	/**
	 * Retrieves the user's phone number.
	 *
	 * @return The phone number.
	 */
	public
	String getPhoneNumber( ) {

		return phoneNumber;
	}

	/**
	 * Updates the user's date of birth.
	 *
	 * @param dateOfBirth
	 * 		The new date of birth in YYYY-MM-DD format.
	 */
	public
	void setDateOfBirth( String dateOfBirth ) {

		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Updates the user's email address.
	 *
	 * @param email
	 * 		The new email address.
	 */
	public
	void setEmail( String email ) {

		this.email = email;
	}

	/**
	 * Updates the user's first name.
	 *
	 * @param firstName
	 * 		The new first name.
	 */
	public
	void setFirstName( String firstName ) {

		this.firstName = firstName;
	}

	/**
	 * Updates the user's last name.
	 *
	 * @param lastName
	 * 		The new last name.
	 */
	public
	void setLastName( String lastName ) {

		this.lastName = lastName;
	}

	/**
	 * Updates the user's phone number.
	 *
	 * @param phoneNumber
	 * 		The new phone number.
	 */
	public
	void setPhoneNumber( String phoneNumber ) {

		this.phoneNumber = phoneNumber;
	}

}
