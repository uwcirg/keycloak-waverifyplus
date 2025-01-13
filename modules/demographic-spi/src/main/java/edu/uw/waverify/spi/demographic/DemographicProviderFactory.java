package edu.uw.waverify.spi.demographic;

import java.util.List;

import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderFactory;

/**
 * Factory class for creating instances of DemographicProvider.
 */
public
class DemographicProviderFactory implements ProviderFactory< DemographicProvider > {

	public static final String PROVIDER_ID = "demographic-validation";

	@Override
	public
	DemographicProvider create( KeycloakSession session ) {

		return new DemographicProviderImpl( session );
	}

	@Override
	public
	void close( ) {
		// Cleanup logic if necessary (e.g., release resources).
	}

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {
		// Return an empty list for now; can be extended to allow custom configurations.
		return List.of( );
	}

}
