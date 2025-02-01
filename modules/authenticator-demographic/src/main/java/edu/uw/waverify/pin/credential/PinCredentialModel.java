package edu.uw.waverify.pin.credential;

import java.io.IOException;

import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialModel;
import org.keycloak.util.JsonSerialization;

import edu.uw.waverify.pin.credential.dto.PinSecretData;

/**
 * Represents a credential model for storing a user's PIN.
 * <p>
 * This class extends {@link CredentialModel} to handle the secure storage and retrieval of PIN credentials in
 * Keycloak.
 * </p>
 */
public
class PinCredentialModel extends CredentialModel {

	/**
	 * The credential type identifier for PIN credentials.
	 */
	public static final String TYPE = "PIN";

	private final PinSecretData secretData;

	/**
	 * Constructs a {@code PinCredentialModel} with the given secret data.
	 *
	 * @param secretData
	 * 		the secret data containing the PIN.
	 */
	private
	PinCredentialModel( PinSecretData secretData ) {

		this.secretData = secretData;
	}

	/**
	 * Constructs a {@code PinCredentialModel} with a plain PIN value.
	 *
	 * @param answer
	 * 		the PIN value.
	 */
	public
	PinCredentialModel( String answer ) {

		this.secretData = new PinSecretData( answer );
	}

	/**
	 * Creates a {@code PinCredentialModel} from an existing {@link CredentialModel}.
	 * <p>
	 * This method extracts the secret data from a stored credential model and initializes a new PIN credential model.
	 * </p>
	 *
	 * @param credentialModel
	 * 		the existing credential model.
	 *
	 * @return a new instance of {@code PinCredentialModel}.
	 *
	 * @throws RuntimeException
	 * 		if an error occurs while deserializing the secret data.
	 */
	public static
	PinCredentialModel createFromCredentialModel( CredentialModel credentialModel ) {

		try {
			PinSecretData secretData = JsonSerialization.readValue( credentialModel.getSecretData( ), PinSecretData.class );

			PinCredentialModel pinCredentialModel = new PinCredentialModel( secretData );
			pinCredentialModel.setUserLabel( credentialModel.getUserLabel( ) );
			pinCredentialModel.setCreatedDate( credentialModel.getCreatedDate( ) );
			pinCredentialModel.setType( TYPE );
			pinCredentialModel.setId( credentialModel.getId( ) );
			pinCredentialModel.setSecretData( credentialModel.getSecretData( ) );
			return pinCredentialModel;
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Creates a new {@code PinCredentialModel} with the given PIN value.
	 * <p>
	 * This method ensures that the credential fields, including secret data, are properly initialized.
	 * </p>
	 *
	 * @param answer
	 * 		the PIN value.
	 *
	 * @return a fully initialized {@code PinCredentialModel}.
	 */
	public static
	PinCredentialModel createPin( String answer ) {

		PinCredentialModel credentialModel = new PinCredentialModel( answer );
		credentialModel.fillCredentialModelFields( );
		return credentialModel;
	}

	/**
	 * Fills the credential model fields, including setting the secret data, type, and creation timestamp.
	 *
	 * @throws RuntimeException
	 * 		if an error occurs while serializing the secret data.
	 */
	private
	void fillCredentialModelFields( ) {

		try {
			setSecretData( JsonSerialization.writeValueAsString( secretData ) );
			setType( TYPE );
			setCreatedDate( Time.currentTimeMillis( ) );
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Retrieves the secret data containing the stored PIN.
	 *
	 * @return the {@code PinSecretData} containing the PIN value.
	 */
	public
	PinSecretData getPinSecretData( ) {

		return secretData;
	}

}
