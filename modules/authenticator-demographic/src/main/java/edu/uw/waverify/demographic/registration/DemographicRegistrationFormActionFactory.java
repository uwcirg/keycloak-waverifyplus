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

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	@Override
	public
	String getDisplayType( ) {

		return "Demographic Registration Form Action";
	}

	@Override
	public
	String getReferenceCategory( ) {

		return "Demographic Validation";
	}

	@Override
	public
	boolean isConfigurable( ) {

		return true;
	}

	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return new Requirement[] { REQUIRED, ALTERNATIVE, DISABLED };
	}

	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	@Override
	public
	String getHelpText( ) {

		return "Collects demographic data during user registration.";
	}

	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return CONFIG_PROPERTIES;
	}

}
