package edu.uw.waverify.pin.credential;

import java.io.IOException;

import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialModel;
import org.keycloak.util.JsonSerialization;

import edu.uw.waverify.pin.credential.dto.PinSecretData;

public
class PinCredentialModel extends CredentialModel {

	public static final String TYPE = "PIN";

	private final PinSecretData secretData;

	private
	PinCredentialModel( PinSecretData secretData ) {

		this.secretData = secretData;
	}

	private
	PinCredentialModel( String answer ) {

		secretData = new PinSecretData( answer );
	}

	public static
	PinCredentialModel createFromCredentialModel( CredentialModel credentialModel ) {

		try {
			PinSecretData secretData = JsonSerialization.readValue( credentialModel.getSecretData( ), PinSecretData.class );

			PinCredentialModel PinCredentialModel = new PinCredentialModel( secretData );
			PinCredentialModel.setUserLabel( credentialModel.getUserLabel( ) );
			PinCredentialModel.setCreatedDate( credentialModel.getCreatedDate( ) );
			PinCredentialModel.setType( TYPE );
			PinCredentialModel.setId( credentialModel.getId( ) );
			PinCredentialModel.setSecretData( credentialModel.getSecretData( ) );
			return PinCredentialModel;
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public static
	PinCredentialModel createPin( String answer ) {

		PinCredentialModel credentialModel = new PinCredentialModel( answer );
		credentialModel.fillCredentialModelFields( );
		return credentialModel;
	}

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

	public
	PinSecretData getPinSecretData( ) {

		return secretData;
	}

}
