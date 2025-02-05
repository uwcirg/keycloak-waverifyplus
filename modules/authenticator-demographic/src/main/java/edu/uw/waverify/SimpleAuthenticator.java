package edu.uw.waverify;

import org.keycloak.authentication.Authenticator;
import org.keycloak.models.*;

public abstract
class SimpleAuthenticator implements Authenticator {

	/**
	 * Cleans up any necessary resources.
	 */
	@Override
	public
	void close( ) {
		// No specific cleanup required.
	}

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
		// No required actions.
	}

}
