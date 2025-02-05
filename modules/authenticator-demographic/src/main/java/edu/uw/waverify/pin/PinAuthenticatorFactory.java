package edu.uw.waverify.pin;

import org.keycloak.authentication.*;
import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.SimpleAuthenticatorFactory;

import lombok.extern.jbosslog.JBossLog;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

/**
 * Factory class for the {@link PinAuthenticator}.
 * <p>
 * This factory is responsible for creating instances of {@code PinAuthenticator} and defining configuration options for
 * the PIN-based authentication process.
 * </p>
 */
@JBossLog
public
class PinAuthenticatorFactory extends SimpleAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

	private static final PinAuthenticator SINGLETON           = new PinAuthenticator( );
	private static final Requirement[]    REQUIREMENT_CHOICES = { REQUIRED, ALTERNATIVE, DISABLED };

	/**
	 * The unique provider ID for the PIN authenticator.
	 */
	public static final String PROVIDER_ID = "pin-authenticator";

	/**
	 * Creates a new instance of the {@code PinAuthenticator}.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return a singleton instance of {@code PinAuthenticator}.
	 */
	@Override
	public
	Authenticator create( KeycloakSession session ) {

		return SINGLETON;
	}

	/**
	 * Retrieves the unique ID of the authenticator provider.
	 *
	 * @return the provider ID.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Retrieves the display name of the authenticator in the Keycloak admin console.
	 *
	 * @return the display name for the PIN authenticator.
	 */
	@Override
	public
	String getDisplayType( ) {

		return "PIN";
	}

	/**
	 * Retrieves the reference category for this authenticator.
	 *
	 * @return the reference category name.
	 */
	@Override
	public
	String getReferenceCategory( ) {

		return "PIN";
	}

	/**
	 * Indicates whether users are allowed to set up this authenticator.
	 *
	 * @return {@code true}, as user setup is allowed.
	 */
	@Override
	public
	boolean isUserSetupAllowed( ) {

		return true;
	}

	/**
	 * Provides a short help text describing the purpose of this authenticator.
	 *
	 * @return a description of the PIN authenticator.
	 */
	@Override
	public
	String getHelpText( ) {

		return "User's PIN.";
	}

	/**
	 * Indicates whether this authenticator can be configured.
	 *
	 * @return {@code true}, as the PIN authenticator supports configuration.
	 */
	@Override
	public
	boolean isConfigurable( ) {

		return true;
	}

	/**
	 * Retrieves the available requirement choices for this authenticator.
	 *
	 * @return an array of available {@link Requirement} options.
	 */
	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return REQUIREMENT_CHOICES;
	}

}
