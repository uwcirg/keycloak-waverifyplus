package edu.uw.waverify.demographic.registration;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

/**
 * Factory class responsible for creating instances of {@link DemographicRegistrationFormAction}.
 * <p>
 * This factory integrates with Keycloak's form-based authentication and enables the collection of demographic data
 * during user registration.
 * </p>
 */
public
class DemographicRegistrationFormActionFactory implements FormActionFactory {

	private static final String                         PROVIDER_ID       = "demographic-registration-form-action";
	private static final List< ProviderConfigProperty > CONFIG_PROPERTIES = new ArrayList<>( );
	private              String                         baseUrl;

	static {
		ProviderConfigProperty baseUrlProperty = new ProviderConfigProperty( );
		baseUrlProperty.setName( "baseUrl" );
		baseUrlProperty.setLabel( "Base URL" );
		baseUrlProperty.setType( ProviderConfigProperty.STRING_TYPE );
		baseUrlProperty.setHelpText( "The base URL for API requests." );
		CONFIG_PROPERTIES.add( baseUrlProperty );

		ProviderConfigProperty verificationTimeout = new ProviderConfigProperty( );
		verificationTimeout.setName( "verification.timeout" );
		verificationTimeout.setLabel( "Verification Timeout" );
		verificationTimeout.setType( ProviderConfigProperty.STRING_TYPE );
		verificationTimeout.setHelpText( "Timeout in seconds for demographic verification." );
		CONFIG_PROPERTIES.add( verificationTimeout );
	}

	/**
	 * Creates a new instance of {@link DemographicRegistrationFormAction}.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return a new {@link DemographicRegistrationFormAction} instance.
	 */
	@Override
	public
	FormAction create( KeycloakSession session ) {

		return new DemographicRegistrationFormAction( session, baseUrl );
	}

	/**
	 * Initializes the factory with configuration parameters.
	 *
	 * @param scope
	 * 		the Keycloak configuration scope.
	 */
	@Override
	public
	void init( Config.Scope scope ) {

		if ( scope != null ) {
			baseUrl = scope.get( "baseUrl" );
		}
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
	 * Cleans up resources when the factory is shut down.
	 */
	@Override
	public
	void close( ) {
		// No cleanup required
	}

	/**
	 * Retrieves the provider ID for this factory.
	 *
	 * @return the provider ID.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Retrieves the display name for this factory in the Keycloak admin console.
	 *
	 * @return the display name.
	 */
	@Override
	public
	String getDisplayType( ) {

		return "Demographic Registration Form Action";
	}

	/**
	 * Retrieves the reference category for this form action.
	 *
	 * @return the reference category.
	 */
	@Override
	public
	String getReferenceCategory( ) {

		return "Demographic Validation";
	}

	/**
	 * Determines whether this form action is configurable.
	 *
	 * @return {@code true}, since configuration is allowed.
	 */
	@Override
	public
	boolean isConfigurable( ) {

		return true;
	}

	/**
	 * Retrieves the supported requirements for this form action.
	 *
	 * @return an array of supported {@link Requirement} values.
	 */
	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return new Requirement[] { REQUIRED, ALTERNATIVE, DISABLED };
	}

	/**
	 * Determines whether user setup is allowed for this form action.
	 *
	 * @return {@code false}, as user setup is not required.
	 */
	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	/**
	 * Provides a help text description for this form action.
	 *
	 * @return a short description of this action.
	 */
	@Override
	public
	String getHelpText( ) {

		return "Collects demographic data during user registration.";
	}

	/**
	 * Retrieves the configuration properties for this form action.
	 *
	 * @return a list of {@link ProviderConfigProperty} instances.
	 */
	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return CONFIG_PROPERTIES;
	}

}
