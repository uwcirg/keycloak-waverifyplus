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

	/**
	 * The unique provider ID for the Noop authenticator.
	 */
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

	/**
	 * Retrieves the unique ID of this authenticator provider.
	 *
	 * @return The provider ID.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Retrieves the display name of the authenticator in the Keycloak admin console.
	 *
	 * @return The display name for the Noop authenticator.
	 */
	@Override
	public
	String getDisplayType( ) {

		return "No-Op Authenticator";
	}

	/**
	 * Retrieves the reference category for this authenticator.
	 *
	 * @return The reference category name.
	 */
	@Override
	public
	String getReferenceCategory( ) {

		return "No-Op Authentication";
	}

	/**
	 * Indicates whether users are allowed to set up this authenticator.
	 *
	 * @return {@code false}, as this authenticator does not require user setup.
	 */
	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	/**
	 * Provides a short help text describing the purpose of this authenticator.
	 *
	 * @return A description of the Noop authenticator.
	 */
	@Override
	public
	String getHelpText( ) {

		return "Prevents authentication from proceeding by presenting an unresolvable challenge.";
	}

}
