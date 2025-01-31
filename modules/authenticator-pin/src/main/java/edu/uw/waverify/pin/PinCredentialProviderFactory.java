package edu.uw.waverify.pin;

import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.pin.credential.PinCredentialModel;

public
class PinCredentialProviderFactory implements CredentialProviderFactory< PinCredentialProvider > {

	public static final String PROVIDER_ID = "pin";

	@Override
	public
	CredentialProvider< PinCredentialModel > create( KeycloakSession session ) {

		return new PinCredentialProvider( session );
	}

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

}
