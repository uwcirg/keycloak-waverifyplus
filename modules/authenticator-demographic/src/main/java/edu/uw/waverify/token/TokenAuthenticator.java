package edu.uw.waverify.token;

import org.keycloak.authentication.*;
import org.keycloak.models.*;

import edu.uw.waverify.SimpleAuthenticator;

import lombok.extern.jbosslog.JBossLog;

import static edu.uw.waverify.token.UserTokenGenerator.TOKEN_ATTRIBUTE;

/**
 * Authenticator that validates a user token and identifies the user.
 * <p>
 * If the token is valid, authentication proceeds to the next step (PIN verification). Otherwise, authentication fails.
 * </p>
 */
@JBossLog
public
class TokenAuthenticator extends SimpleAuthenticator implements Authenticator {

	private static final String TOKEN_PARAM = "user_token";

	/**
	 * Authenticates a user based on the provided token.
	 * <p>
	 * If the token is valid and a user is found, authentication is successful. Otherwise, it fails with an appropriate
	 * error.
	 * </p>
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		var token = context.getHttpRequest( )
		                   .getUri( )
		                   .getQueryParameters( )
		                   .getFirst( TOKEN_PARAM );

		if ( token == null || token.isBlank( ) ) {
			log.warn( "No token found in request parameters" );
			context.failure( AuthenticationFlowError.INVALID_CREDENTIALS );
			return;
		}

		var user = findUserByToken( context.getSession( ), context.getRealm( ), token );
		if ( user == null ) {
			log.warn( "No such user found in request parameters" );
			log.warn( "token: " + token );
			context.failure( AuthenticationFlowError.UNKNOWN_USER );
			return;
		}

		context.setUser( user );
		context.success( );
	}

	/**
	 * Processes an authentication action.
	 * <p>
	 * This implementation simply calls {@link #authenticate(AuthenticationFlowContext)}.
	 * </p>
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
	@Override
	public
	void action( AuthenticationFlowContext context ) {

		authenticate( context );
	}

	/**
	 * Checks whether the authenticator is configured for the given user.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param realm
	 * 		the realm.
	 * @param user
	 * 		the user.
	 *
	 * @return {@code true}, indicating that this authenticator does not require per-user configuration.
	 */
	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return true;
	}

	/**
	 * Finds a user by validating the provided token.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param realm
	 * 		the realm in which to search for the user.
	 * @param token
	 * 		the authentication token.
	 *
	 * @return the identified user, or {@code null} if no valid user is found.
	 */
	private
	UserModel findUserByToken( KeycloakSession session, RealmModel realm, String token ) {

		var userProvider = session.users( );
		var user = userProvider.searchForUserByUserAttributeStream( realm, TOKEN_ATTRIBUTE, token )
		                       .findFirst( );
		return user.orElse( null );
	}

}
