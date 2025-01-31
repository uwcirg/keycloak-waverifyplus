package edu.uw.waverify.token;

import org.keycloak.authentication.*;
import org.keycloak.models.*;

import lombok.extern.jbosslog.JBossLog;

import static edu.uw.waverify.token.UserTokenGenerator.TOKEN_ATTRIBUTE;

/**
 * Authenticator that validates a user token and identifies the user. If the token is valid, authentication proceeds to
 * the next step (PIN verification).
 */
@JBossLog
public
class TokenAuthenticator implements Authenticator {

	private static final String TOKEN_PARAM = "user_token";

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

	@Override
	public
	void action( AuthenticationFlowContext context ) {

		authenticate( context );
	}

	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return true;
	}

	@Override
	public
	void setRequiredActions( KeycloakSession session, RealmModel realm, UserModel user ) {
		// No required actions
	}

	@Override
	public
	void close( ) {
		// No resources to close
	}

	/**
	 * Finds a user by validating the provided token.
	 *
	 * @param session
	 * 		The Keycloak session.
	 * @param realm
	 * 		The realm in which to search for the user.
	 * @param token
	 * 		The authentication token.
	 *
	 * @return The identified user, or null if no valid user is found.
	 */
	private
	UserModel findUserByToken( KeycloakSession session, RealmModel realm, String token ) {

		var userProvider = session.users( );
		var user = userProvider.searchForUserByUserAttributeStream( realm, TOKEN_ATTRIBUTE, token )
		                       .findFirst( );
		return user.orElse( null );
	}

}
