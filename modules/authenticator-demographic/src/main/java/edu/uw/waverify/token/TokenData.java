package edu.uw.waverify.token;

import lombok.Data;

@Data
public
class TokenData {

	public final String nonce;
	public final String hashedToken;

}
