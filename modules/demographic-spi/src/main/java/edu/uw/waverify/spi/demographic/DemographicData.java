package edu.uw.waverify.spi.demographic;

import java.util.Objects;

/**
 * A data class representing demographic information provided by the user.
 */
public
class DemographicData {

	private String firstName;
	private String lastName;
	private String dateOfBirth; // Format: YYYY-MM-DD
	private String email;
	private String phoneNumber;

	/**
	 * Default constructor.
	 */
	public
	DemographicData( ) {

	}

	/**
	 * Constructor with all fields.
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

	// Getters and Setters

	@Override
	public
	int hashCode( ) {

		return Objects.hash( firstName, lastName, dateOfBirth, email, phoneNumber );
	}

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

	@Override
	public
	String toString( ) {

		return "DemographicData{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", dateOfBirth='" + dateOfBirth + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
	}

	public
	String getDateOfBirth( ) {

		return dateOfBirth;
	}

	public
	String getEmail( ) {

		return email;
	}

	public
	String getFirstName( ) {

		return firstName;
	}

	public
	String getLastName( ) {

		return lastName;
	}

	public
	String getPhoneNumber( ) {

		return phoneNumber;
	}

	public
	void setDateOfBirth( String dateOfBirth ) {

		this.dateOfBirth = dateOfBirth;
	}

	public
	void setEmail( String email ) {

		this.email = email;
	}

	// Utility methods

	public
	void setFirstName( String firstName ) {

		this.firstName = firstName;
	}

	public
	void setLastName( String lastName ) {

		this.lastName = lastName;
	}

	public
	void setPhoneNumber( String phoneNumber ) {

		this.phoneNumber = phoneNumber;
	}

}
