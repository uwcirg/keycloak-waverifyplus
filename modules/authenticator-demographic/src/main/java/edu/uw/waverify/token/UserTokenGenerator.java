package edu.uw.waverify.token;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

import org.keycloak.models.UserModel;

/**
 * Utility for generating and storing unique user tokens for authentication.
 */
public
class UserTokenGenerator {

	public static final String NONCE_ATTRIBUTE = "auth_nonce";
	public static final String TOKEN_ATTRIBUTE = "auth_token";

	/**
	 * Generates and stores a persistent unique token for the user.
	 *
	 * @param user
	 * 		The user for whom the token is generated.
	 *
	 * @return The generated token data.
	 */
	public static
	TokenData generateAndStoreToken( UserModel user ) {

		var nonce       = generateSecureNonce( );
		var hashedToken = hashUserToken( user.getId( ), nonce );

		storeTokenInUser( user, nonce, hashedToken );
		return new TokenData( nonce, hashedToken );
	}

	/**
	 * Generates a cryptographically secure random nonce.
	 *
	 * @return A base64-encoded random string.
	 */
	private static
	String generateSecureNonce( ) {

		byte[] randomBytes = new byte[ 16 ]; // 128-bit nonce
		new SecureRandom( ).nextBytes( randomBytes );
		return Base64.getUrlEncoder( )
		             .withoutPadding( )
		             .encodeToString( randomBytes );
	}

	/**
	 * Hashes the user ID and nonce using SHA-256.
	 *
	 * @param userId
	 * 		The user's unique ID.
	 * @param nonce
	 * 		The secure random nonce.
	 *
	 * @return The base64-encoded SHA-256 hash.
	 */
	private static
	String hashUserToken( String userId, String nonce ) {

		try {
			var digest   = MessageDigest.getInstance( "SHA-256" );
			var combined = userId + ":" + nonce;
			var hash     = digest.digest( combined.getBytes( StandardCharsets.UTF_8 ) );
			return Base64.getUrlEncoder( )
			             .withoutPadding( )
			             .encodeToString( hash );
		} catch ( NoSuchAlgorithmException e ) {
			throw new RuntimeException( "Error generating token hash", e );
		}
	}

	/**
	 * Retrieves the stored token data from the user attributes.
	 *
	 * @param user
	 * 		The user whose token is retrieved.
	 *
	 * @return The stored TokenData, or null if not found.
	 */
	public static
	TokenData retrieveStoredToken( UserModel user ) {

		var nonce       = user.getFirstAttribute( NONCE_ATTRIBUTE );
		var hashedToken = user.getFirstAttribute( TOKEN_ATTRIBUTE );
		return ( nonce != null && hashedToken != null ) ? new TokenData( nonce, hashedToken ) : null;
	}

	/**
	 * Stores the generated token data in the user's attributes.
	 *
	 * @param user
	 * 		The user model.
	 * @param nonce
	 * 		The secure nonce.
	 * @param hashedToken
	 * 		The hashed token.
	 */
	private static
	void storeTokenInUser( UserModel user, String nonce, String hashedToken ) {

		user.setSingleAttribute( NONCE_ATTRIBUTE, nonce );
		user.setSingleAttribute( TOKEN_ATTRIBUTE, hashedToken );
	}

}
