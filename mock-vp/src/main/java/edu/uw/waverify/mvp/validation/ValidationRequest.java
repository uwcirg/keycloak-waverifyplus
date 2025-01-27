package edu.uw.waverify.mvp.validation;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

public
class ValidationRequest {

	@NotBlank(message = "First name is required.")
	private String firstName;

	@NotBlank(message = "Last name is required.")
	private String lastName;

	@NotNull(message = "Date of birth is required.")
	@Past(message = "Date of birth must be in the past.")
	private LocalDate dob;

	public
	LocalDate getDob( ) {

		return dob;
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
	void setDob( LocalDate dob ) {

		this.dob = dob;
	}

	public
	void setFirstName( String firstName ) {

		this.firstName = firstName;
	}

	public
	void setLastName( String lastName ) {

		this.lastName = lastName;
	}

}
