package edu.uw.waverify.noop.authenticator;

import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import lombok.extern.jbosslog.JBossLog;

import static java.util.Collections.emptyList;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

/**
 * Factory class responsible for creating and managing instances of {@link NoopAuthenticator}.
 * <p>
 * This factory ensures that the authenticator is correctly initialized and prevents authentication from proceeding by
 * directing users to an unresolvable challenge.
 * </p>
 */
@JBossLog
public
class NoopAuthenticatorFactory implements AuthenticatorFactory {

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
	 * Initializes the factory with the provided configuration scope.
	 *
	 * @param config
	 * 		The configuration scope.
	 */
	@Override
	public
	void init( Config.Scope config ) {
		// No initialization required
	}

	/**
	 * Performs post-initialization tasks after the Keycloak session factory is created.
	 *
	 * @param factory
	 * 		The Keycloak session factory.
	 */
	@Override
	public
	void postInit( KeycloakSessionFactory factory ) {

		log.info( "NoopAuthenticatorFactory initialized." );
	}

	/**
	 * Cleans up resources when the factory is closed.
	 */
	@Override
	public
	void close( ) {
		// No cleanup required
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
	boolean isConfigurable( ) {

		return false;
	}

	/**
	 * Defines the requirement choices for this authenticator.
	 * <p>
	 * Since this authenticator blocks authentication, it cannot be set as ALTERNATIVE.
	 * </p>
	 *
	 * @return An array containing {@link Requirement#REQUIRED} and {@link Requirement#DISABLED}.
	 */
	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return new Requirement[] { REQUIRED, DISABLED };
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

	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return emptyList( );
	}

}
