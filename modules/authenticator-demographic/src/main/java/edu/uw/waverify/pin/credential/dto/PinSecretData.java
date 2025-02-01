package edu.uw.waverify.pin.credential.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Represents the secret data for a PIN credential.
 * <p>
 * This class is used to store and deserialize the PIN value securely.
 * </p>
 */
@Getter
public
class PinSecretData {

	private final String pin;

	/**
	 * Constructs a new {@code PinSecretData} instance with the given PIN value.
	 *
	 * @param pin
	 * 		the PIN value associated with the credential.
	 */
	@JsonCreator
	public
	PinSecretData( @JsonProperty("pin") String pin ) {

		this.pin = pin;
	}

}
