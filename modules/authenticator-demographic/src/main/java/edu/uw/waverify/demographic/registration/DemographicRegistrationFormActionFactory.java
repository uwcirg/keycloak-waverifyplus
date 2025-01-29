package edu.uw.waverify.demographic.registration;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.*;
import org.keycloak.provider.ProviderConfigProperty;

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

		return new DemographicRegistrationFormAction( );
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
	 * Cleans up resources when the factory is shut down.
	 */
	@Override
	public
	void close( ) {
		// No cleanup required
	}

	/**
	 * Returns the unique provider ID for this factory.
	 *
	 * @return the provider ID as a string.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Returns the display name for this form action.
	 *
	 * @return the display name as a string.
	 */
	@Override
	public
	String getDisplayType( ) {

		return "Demographic Registration Form Action";
	}

	/**
	 * Returns the reference category for this form action.
	 *
	 * @return an empty string as no reference category is defined.
	 */
	@Override
	public
	String getReferenceCategory( ) {

		return "";
	}

	/**
	 * Indicates whether this form action is configurable.
	 *
	 * @return {@code false} as no additional configuration is needed.
	 */
	@Override
	public
	boolean isConfigurable( ) {

		return false;
	}

	/**
	 * Defines the requirement choices available for this form action.
	 *
	 * @return an array of possible requirement choices.
	 */
	@Override
	public
	AuthenticationExecutionModel.Requirement[] getRequirementChoices( ) {

		return new AuthenticationExecutionModel.Requirement[] { REQUIRED, ALTERNATIVE, DISABLED };
	}

	/**
	 * Indicates whether user setup is allowed for this form action.
	 *
	 * @return {@code false} as user setup is not applicable.
	 */
	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	/**
	 * Provides a brief description of this form action.
	 *
	 * @return the help text as a string.
	 */
	@Override
	public
	String getHelpText( ) {

		return "Collects demographic data during user registration.";
	}

	/**
	 * Retrieves the configuration properties for this form action.
	 *
	 * @return an empty list as no configuration properties are defined.
	 */
	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return CONFIG_PROPERTIES;
	}

}
