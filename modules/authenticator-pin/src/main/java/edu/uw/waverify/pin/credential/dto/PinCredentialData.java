package edu.uw.waverify.pin.credential.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public
class PinCredentialData {

	private final String pin;

	@JsonCreator
	public
	PinCredentialData( @JsonProperty("pin") String pin ) {

		this.pin = pin;
	}

}
