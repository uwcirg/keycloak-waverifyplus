package edu.uw.waverify.token;

import java.util.List;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import static org.keycloak.Config.Scope;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

/**
 * Factory for creating instances of {@link TokenAuthenticator}.
 * <p>
 * This factory registers the token authenticator within Keycloak and defines its configuration settings.
 * </p>
 */
public
class TokenAuthenticatorFactory implements AuthenticatorFactory {

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
	 * Initializes the factory.
	 *
	 * @param scope
	 * 		the configuration scope.
	 */
	@Override
	public
	void init( Scope scope ) {
		// No initialization required
	}

	/**
	 * Performs post-initialization tasks.
	 *
	 * @param factory
	 * 		the Keycloak session factory.
	 */
	@Override
	public
	void postInit( KeycloakSessionFactory factory ) {
		// No post-initialization required
	}

	/**
	 * Closes the factory and releases any resources.
	 */
	@Override
	public
	void close( ) {
		// No resources to close
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
	 * Indicates whether this authenticator is configurable.
	 *
	 * @return {@code false}, as it has no configurable properties.
	 */
	@Override
	public
	boolean isConfigurable( ) {

		return false;
	}

	/**
	 * Returns the allowed requirement choices for this authenticator.
	 *
	 * @return an array containing {@code REQUIRED} and {@code DISABLED}.
	 */
	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return new Requirement[] { REQUIRED, DISABLED };
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

	/**
	 * Returns the configuration properties for this authenticator.
	 *
	 * @return an empty list, as no configuration is required.
	 */
	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return List.of( );
	}

}
