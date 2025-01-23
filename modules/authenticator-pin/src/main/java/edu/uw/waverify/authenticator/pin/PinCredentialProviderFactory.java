package edu.uw.waverify.authenticator.pin;

import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.models.KeycloakSession;

public
class PinCredentialProviderFactory implements CredentialProviderFactory< PinCredentialProvider > {

	public static final String PROVIDER_ID = "pin";

	@Override
	public
	CredentialProvider create( KeycloakSession session ) {

		return new PinCredentialProvider( session );
	}

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

}
