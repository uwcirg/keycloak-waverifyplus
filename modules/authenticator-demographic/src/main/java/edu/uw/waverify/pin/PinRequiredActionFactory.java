package edu.uw.waverify.pin;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * Factory for creating instances of {@link PinRequiredAction}.
 * <p>
 * This factory registers the PIN configuration required action in Keycloak.
 * </p>
 */
public
class PinRequiredActionFactory implements RequiredActionFactory {

	private static final PinRequiredAction SINGLETON = new PinRequiredAction( );

	/**
	 * Creates an instance of the required action provider.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return the singleton instance of {@link PinRequiredAction}.
	 */
	@Override
	public
	RequiredActionProvider create( KeycloakSession session ) {

		return SINGLETON;
	}

	/**
	 * Initializes the factory with the provided configuration.
	 *
	 * @param config
	 * 		the configuration scope.
	 */
	@Override
	public
	void init( Config.Scope config ) {

	}

	/**
	 * Performs post-initialization tasks after Keycloak startup.
	 *
	 * @param factory
	 * 		the Keycloak session factory.
	 */
	@Override
	public
	void postInit( KeycloakSessionFactory factory ) {

	}

	/**
	 * Closes any resources used by this factory.
	 */
	@Override
	public
	void close( ) {

	}

	/**
	 * Gets the unique identifier of this required action factory.
	 *
	 * @return the provider ID of the PIN required action.
	 */
	@Override
	public
	String getId( ) {

		return PinRequiredAction.PROVIDER_ID;
	}

	/**
	 * Gets the display name of the required action in the Keycloak admin UI.
	 *
	 * @return the display name ("PIN").
	 */
	@Override
	public
	String getDisplayText( ) {

		return "PIN";
	}

}
