package edu.uw.waverify.spi.demographic;

import java.util.List;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderFactory;

/**
 * Factory class for creating instances of DemographicProvider.
 */
public
class DemographicProviderFactory implements ProviderFactory< DemographicProvider > {

	public static final String                         PROVIDER_ID = "demographic-validation";
	private final       DemographicVerificationService verificationService;

	public
	DemographicProviderFactory( DemographicVerificationService verificationService ) {

		this.verificationService = verificationService;
	}

	@Override
	public
	DemographicProvider create( KeycloakSession session ) {

		return new DemographicProviderImpl( session, verificationService );
	}

	@Override
	public
	void init( Config.Scope scope ) {

	}

	@Override
	public
	void postInit( KeycloakSessionFactory keycloakSessionFactory ) {

	}

	@Override
	public
	void close( ) {

	}

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	@Override
	public
	int order( ) {

		return 0;
	}

	@Override
	public
	List< ProviderConfigProperty > getConfigMetadata( ) {

		return ProviderFactory.super.getConfigMetadata( );
	}

}
