package edu.uw.waverify.token;

import lombok.Data;

/**
 * Represents token-related data, including a nonce and a hashed token.
 */
@Data
public
class TokenData {

	/**
	 * A unique nonce value associated with the token.
	 */
	public final String nonce;

	/**
	 * The hashed representation of the token.
	 */
	public final String hashedToken;

}
