package edu.uw.waverify;

import org.keycloak.authentication.Authenticator;
import org.keycloak.models.*;

/**
 * A base class for Keycloak authenticators that provides default implementations for common methods.
 */
public abstract
class SimpleAuthenticator implements Authenticator {

	/**
	 * Cleans up any necessary resources.
	 */
	@Override
	public
	void close( ) {

	}

	/**
	 * Indicates whether this authenticator requires a user to be authenticated.
	 *
	 * @return {@code false}, meaning no user is required.
	 */
	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	/**
	 * Defines any required actions for the user.
	 *
	 * @param keycloakSession
	 * 		the Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 */
	@Override
	public
	void setRequiredActions( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {

	}

}
