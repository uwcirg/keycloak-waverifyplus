package edu.uw.waverify.spi.demographic;

import java.util.List;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderFactory;

import edu.uw.waverify.spi.demographic.verification.DemographicVerificationService;
import edu.uw.waverify.spi.demographic.verification.DemographicVerificationServiceImpl;

/**
 * Factory class responsible for creating and managing instances of {@link DemographicProvider}. This factory
 * initializes required services and ensures proper lifecycle management for demographic validation providers.
 */
public
class DemographicProviderFactory implements ProviderFactory< DemographicProvider > {

	/**
	 * The unique identifier for the demographic validation provider.
	 */
	public static final String PROVIDER_ID = "demographic-validation";

	/**
	 * Service used for demographic verification logic.
	 */
	private DemographicVerificationService verificationService;

	/**
	 * Creates a new instance of {@link DemographicProvider} for the given session.
	 *
	 * @param session
	 * 		The Keycloak session for which the provider is created.
	 *
	 * @return A new instance of {@link DemographicProvider}.
	 */
	@Override
	public
	DemographicProvider create( KeycloakSession session ) {

		return new DemographicProviderImpl( session, verificationService );
	}

	/**
	 * Initializes the factory with the provided configuration scope.
	 *
	 * @param scope
	 * 		The configuration scope containing initialization parameters.
	 */
	@Override
	public
	void init( Config.Scope scope ) {

		this.verificationService = new DemographicVerificationServiceImpl( );
	}

	/**
	 * Performs post-initialization tasks after the Keycloak session factory is created.
	 *
	 * @param keycloakSessionFactory
	 * 		The Keycloak session factory.
	 */
	@Override
	public
	void postInit( KeycloakSessionFactory keycloakSessionFactory ) {
		// No additional post-initialization tasks are required.
	}

	/**
	 * Cleans up resources when the factory is closed.
	 */
	@Override
	public
	void close( ) {
		// No specific cleanup tasks are required.
	}

	/**
	 * Returns the unique identifier for this provider factory.
	 *
	 * @return The provider ID.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	/**
	 * Defines the order of the provider factory in the initialization process.
	 *
	 * @return The order value (lower values indicate higher priority).
	 */
	@Override
	public
	int order( ) {

		return 0;
	}

	/**
	 * Retrieves metadata for configuration properties supported by this factory.
	 *
	 * @return A list of {@link ProviderConfigProperty} objects, or an empty list if none.
	 */
	@Override
	public
	List< ProviderConfigProperty > getConfigMetadata( ) {

		return ProviderFactory.super.getConfigMetadata( );
	}

	/**
	 * Sets a custom demographic verification service, primarily for testing or advanced use cases.
	 *
	 * @param verificationService
	 * 		The custom {@link DemographicVerificationService} to use.
	 */
	public
	void setDemographicVerificationService( DemographicVerificationService verificationService ) {

		this.verificationService = verificationService;
	}

}
