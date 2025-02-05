package edu.uw.waverify.noop.authenticator;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.SimpleAuthenticatorFactory;

import lombok.extern.jbosslog.JBossLog;

/**
 * Factory class responsible for creating and managing instances of {@link NoopAuthenticator}.
 * <p>
 * This factory ensures that the authenticator is correctly initialized and prevents authentication from proceeding by
 * directing users to an unresolvable challenge.
 * </p>
 */
@JBossLog
public
class NoopAuthenticatorFactory extends SimpleAuthenticatorFactory implements AuthenticatorFactory {

	public static final String PROVIDER_ID = "noop-authenticator";

	/**
	 * Creates a new instance of {@link NoopAuthenticator}.
	 *
	 * @param session
	 * 		The Keycloak session for which the authenticator is created.
	 *
	 * @return A new instance of {@link NoopAuthenticator}.
	 */
	@Override
	public
	Authenticator create( KeycloakSession session ) {

		return new NoopAuthenticator( );
	}

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	@Override
	public
	String getDisplayType( ) {

		return "No-Op Authenticator";
	}

	@Override
	public
	String getReferenceCategory( ) {

		return "No-Op Authentication";
	}

	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	@Override
	public
	String getHelpText( ) {

		return "Prevents authentication from proceeding by presenting an unresolvable challenge.";
	}

}
