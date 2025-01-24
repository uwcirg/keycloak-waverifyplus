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
 * Factory class for creating instances of {@link DemographicRegistrationFormAction}.
 */
public
class DemographicRegistrationFormActionFactory implements FormActionFactory {

	private static final String                         PROVIDER_ID       = "demographic-registration-form-action";
	private static final List< ProviderConfigProperty > CONFIG_PROPERTIES = new ArrayList<>( );

	/**
	 * Creates an instance of {@link DemographicRegistrationFormAction}.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return a new instance of {@link DemographicRegistrationFormAction}.
	 */
	@Override
	public
	FormAction create( KeycloakSession session ) {

		return new DemographicRegistrationFormAction( );
	}

	/**
	 * Initializes any necessary configurations.
	 *
	 * @param scope
	 * 		the Keycloak configuration scope.
	 */
	@Override
	public
	void init( Config.Scope scope ) {

	}

	/**
	 * Post-initialization tasks.
	 *
	 * @param factory
	 * 		the Keycloak session factory.
	 */
	@Override
	public
	void postInit( KeycloakSessionFactory factory ) {

	}

	/**
	 * Cleans up resources during shutdown.
	 */
	@Override
	public
	void close( ) {

	}

	/**
	 * Returns the provider ID for this form action factory.
	 *
	 * @return the provider ID as a string.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Returns the display type for this form action.
	 *
	 * @return the display type as a string.
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
	 * @return {@code false} as this action requires no configuration.
	 */
	@Override
	public
	boolean isConfigurable( ) {

		return false;
	}

	/**
	 * Returns the possible requirement choices for this form action.
	 *
	 * @return an array of possible requirement choices.
	 */
	@Override
	public
	AuthenticationExecutionModel.Requirement[] getRequirementChoices( ) {

		return new AuthenticationExecutionModel.Requirement[] { REQUIRED, ALTERNATIVE, DISABLED };
	}

	/**
	 * Indicates whether this form action allows user setup.
	 *
	 * @return {@code false} as user setup is not allowed.
	 */
	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	/**
	 * Returns the help text for this form action.
	 *
	 * @return the help text as a string.
	 */
	@Override
	public
	String getHelpText( ) {

		return "Collects demographic data during user registration.";
	}

	/**
	 * Returns configuration properties for this form action.
	 *
	 * @return an empty list as no configuration properties are defined.
	 */
	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return CONFIG_PROPERTIES;
	}

}
