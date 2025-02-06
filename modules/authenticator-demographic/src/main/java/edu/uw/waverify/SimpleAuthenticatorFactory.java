package edu.uw.waverify;

import java.lang.reflect.Field;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import org.jboss.logging.Logger;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

/**
 * A base class for Keycloak authenticator factories that simplifies the implementation by providing default behavior
 * for common methods.
 */
public abstract
class SimpleAuthenticatorFactory implements AuthenticatorFactory {

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

	/**
	 * Initializes the factory.
	 *
	 * @param scope
	 * 		the configuration scope.
	 */
	@Override
	public
	void init( Config.Scope scope ) {

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

		getLog( ).info( "Initialized: " + this.getClass( )
		                                      .getName( ) );
	}

	/**
	 * Closes the factory and releases any resources.
	 */
	@Override
	public
	void close( ) {

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

	/**
	 * Retrieves the logger instance for this factory.
	 *
	 * @return the logger associated with the class.
	 *
	 * @throws IllegalStateException
	 * 		if the logger field cannot be accessed.
	 */
	public
	Logger getLog( ) {

		try {
			Field logField = this.getClass( )
			                     .getDeclaredField( "log" );
			logField.setAccessible( true );
			return ( Logger ) logField.get( this );
		} catch ( NoSuchFieldException |
		          IllegalAccessException e ) {
			throw new IllegalStateException( "Failed to retrieve logger via reflection", e );
		}
	}

}
