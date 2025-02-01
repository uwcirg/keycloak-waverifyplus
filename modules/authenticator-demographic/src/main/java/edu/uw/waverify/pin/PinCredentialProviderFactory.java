package edu.uw.waverify.pin;

import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialProviderFactory;
import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.pin.credential.PinCredentialModel;

import static edu.uw.waverify.pin.credential.PinCredentialModel.TYPE;

/**
 * Factory for creating instances of {@link PinCredentialProvider}.
 * <p>
 * This factory is responsible for providing the PIN credential provider used in authentication and credential
 * management.
 * </p>
 */
public
class PinCredentialProviderFactory implements CredentialProviderFactory< PinCredentialProvider > {

	/**
	 * The provider ID, which corresponds to the PIN credential type.
	 */
	public static final String PROVIDER_ID = TYPE;

	/**
	 * Creates a new instance of {@link PinCredentialProvider} for the given session.
	 *
	 * @param session
	 * 		the Keycloak session.
	 *
	 * @return a new instance of {@code PinCredentialProvider}.
	 */
	@Override
	public
	CredentialProvider< PinCredentialModel > create( KeycloakSession session ) {

		return new PinCredentialProvider( session );
	}

	/**
	 * Retrieves the identifier of this credential provider factory.
	 *
	 * @return the provider ID.
	 */
	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

}
