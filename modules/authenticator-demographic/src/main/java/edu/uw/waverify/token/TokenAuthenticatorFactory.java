package edu.uw.waverify.token;

import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.SimpleAuthenticatorFactory;

import lombok.extern.jbosslog.JBossLog;

/**
 * Factory for creating instances of {@link TokenAuthenticator}.
 * <p>
 * This factory registers the token authenticator within Keycloak and defines its configuration settings.
 * </p>
 */
@JBossLog
public
class TokenAuthenticatorFactory extends SimpleAuthenticatorFactory {

	/**
	 * The unique provider ID for this authenticator.
	 */
	public static final String PROVIDER_ID = "token-authenticator";

	/**
	 * Creates a new instance of {@link TokenAuthenticator}.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return a new {@link TokenAuthenticator} instance.
	 */
	@Override
	public
	Authenticator create( KeycloakSession session ) {

		return new TokenAuthenticator( );
	}

	/**
	 * Returns the unique ID of this authenticator provider.
	 *
	 * @return the provider ID.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Returns the display name of this authenticator.
	 *
	 * @return "Token Authenticator".
	 */
	@Override
	public
	String getDisplayType( ) {

		return "Token Authenticator";
	}

	/**
	 * Returns the category reference for this authenticator.
	 *
	 * @return "token".
	 */
	@Override
	public
	String getReferenceCategory( ) {

		return "token";
	}

	/**
	 * Indicates whether this authenticator allows user setup.
	 *
	 * @return {@code false}, as it does not require per-user configuration.
	 */
	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	/**
	 * Returns a brief description of this authenticator.
	 *
	 * @return "Token Authenticator".
	 */
	@Override
	public
	String getHelpText( ) {

		return "Token Authenticator";
	}

}
