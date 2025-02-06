package edu.uw.waverify.demographic.authenticator;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;

import edu.uw.waverify.SimpleAuthenticatorFactory;

import lombok.extern.jbosslog.JBossLog;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

/**
 * Factory class for creating instances of {@link DemographicAuthenticator}.
 * <p>
 * This factory initializes required services and manages the lifecycle of demographic validation authenticators.
 * </p>
 */
@JBossLog
public
class DemographicAuthenticatorFactory extends SimpleAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

	private static final List< ProviderConfigProperty > CONFIG_PROPERTIES   = new ArrayList<>( );
	private static final Requirement[]                  REQUIREMENT_CHOICES = { REQUIRED, ALTERNATIVE, DISABLED };
	public static final  String                         PROVIDER_ID         = "demographic-validation-authenticator";

	private String baseUrl;

	static {
		var baseUrlProperty = new ProviderConfigProperty( );
		baseUrlProperty.setName( "baseUrl" );
		baseUrlProperty.setLabel( "Base URL" );
		baseUrlProperty.setType( ProviderConfigProperty.STRING_TYPE );
		baseUrlProperty.setHelpText( "Base URL for demographic verification service." );
		CONFIG_PROPERTIES.add( baseUrlProperty );
	}

	/**
	 * Creates a new instance of {@link DemographicAuthenticator}.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return a new instance of {@link DemographicAuthenticator}.
	 */
	@Override
	public
	DemographicAuthenticator create( KeycloakSession session ) {

		try {
			return new DemographicAuthenticatorImpl( session, baseUrl );
		} catch ( Exception e ) {
			log.error( "Error creating DemographicAuthenticator", e );
			throw new RuntimeException( "Failed to create DemographicAuthenticator", e );
		}
	}

	/**
	 * Returns the unique provider ID.
	 *
	 * @return the provider ID.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Returns the configurable properties for this authenticator.
	 *
	 * @return a list of {@link ProviderConfigProperty}.
	 */
	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return CONFIG_PROPERTIES;
	}

	/**
	 * Initializes the factory with configuration values.
	 *
	 * @param config
	 * 		the configuration scope.
	 */
	@Override
	public
	void init( Config.Scope config ) {

		if ( config != null ) {
			baseUrl = config.get( "baseUrl" );
		}
	}

	/**
	 * Determines if this authenticator is configurable.
	 *
	 * @return {@code true}, indicating that configuration is allowed.
	 */
	@Override
	public
	boolean isConfigurable( ) {

		return true;
	}

	/**
	 * Returns the available requirement choices.
	 *
	 * @return an array of supported {@link Requirement} values.
	 */
	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return REQUIREMENT_CHOICES;
	}

	/**
	 * Returns the display name of this authenticator.
	 *
	 * @return the display name.
	 */
	@Override
	public
	String getDisplayType( ) {

		return "Demographic Validation";
	}

	/**
	 * Returns the reference category for this authenticator.
	 *
	 * @return the reference category.
	 */
	@Override
	public
	String getReferenceCategory( ) {

		return "Demographic Validation";
	}

	/**
	 * Determines if user setup is allowed.
	 *
	 * @return {@code true}, indicating that users can configure this authenticator.
	 */
	@Override
	public
	boolean isUserSetupAllowed( ) {

		return true;
	}

	/**
	 * Returns help text for this authenticator.
	 *
	 * @return a description of the authenticator's purpose.
	 */
	@Override
	public
	String getHelpText( ) {

		return "Demographic validation authenticator for verifying user demographic information.";
	}

}
