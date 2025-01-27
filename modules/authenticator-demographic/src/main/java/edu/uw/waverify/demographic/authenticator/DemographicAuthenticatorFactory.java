package edu.uw.waverify.demographic.authenticator;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.*;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationService;
import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationServiceImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

/**
 * Factory class responsible for creating and managing instances of {@link DemographicAuthenticator}. This factory
 * initializes required services and ensures proper lifecycle management for demographic validation authenticators.
 */
@JBossLog
public
class DemographicAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

	private static final List< ProviderConfigProperty > CONFIG_PROPERTIES    = new ArrayList<>( );
	private static final Requirement[]                  REQUIREMENT_CHOICES  = { REQUIRED, ALTERNATIVE, DISABLED };
	public static final  String                         VERIFICATION_TIMEOUT = "verification.timeout";
	public static final  String                         PROVIDER_ID          = "demographic-validation-authenticator";

	@Setter
	@Getter
	private static DemographicVerificationService verificationService;

	static {
		ProviderConfigProperty property = new ProviderConfigProperty( );
		property.setName( VERIFICATION_TIMEOUT );
		property.setLabel( "Verification Timeout" );
		property.setType( ProviderConfigProperty.STRING_TYPE );
		property.setHelpText( "Timeout in seconds for demographic verification." );
		CONFIG_PROPERTIES.add( property );
	}

	/**
	 * Creates a new instance of {@link DemographicAuthenticator} for the given session.
	 *
	 * @param session
	 * 		The Keycloak session for which the authenticator is created.
	 *
	 * @return A new instance of {@link DemographicAuthenticator}.
	 */
	@Override
	public
	Authenticator create( KeycloakSession session ) {

		try {
			if ( verificationService == null ) {
				verificationService = new DemographicVerificationServiceImpl( session );
			}
			return new DemographicAuthenticatorImpl( session, verificationService );
		} catch ( Exception e ) {
			log.error( "Error creating DemographicAuthenticator", e );
			throw new RuntimeException( "Failed to create DemographicAuthenticator", e );
		}
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

		if ( config != null ) {
			String timeout = config.get( VERIFICATION_TIMEOUT );
			if ( timeout != null && !timeout.matches( "\\d+" ) ) {
				throw new IllegalArgumentException( "Invalid timeout value. Must be a positive integer." );
			}
			log.info( "Initializing DemographicAuthenticatorFactory with timeout: " + timeout );
		}
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

		log.info( "Post-initializing DemographicAuthenticatorFactory..." );
	}

	/**
	 * Cleans up resources when the factory is closed.
	 */
	@Override
	public
	void close( ) {

		verificationService = null;
	}

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	@Override
	public
	String getDisplayType( ) {

		return "Demographic Validation";
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

		return REQUIREMENT_CHOICES;
	}

	@Override
	public
	boolean isUserSetupAllowed( ) {

		return true;
	}

	@Override
	public
	String getHelpText( ) {

		return "Demographic validation authenticator for verifying user demographic information.";
	}

	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return CONFIG_PROPERTIES;
	}

}
