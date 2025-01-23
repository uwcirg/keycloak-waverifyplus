package edu.uw.waverify.authenticator.pin.credential;

import java.io.IOException;

import org.keycloak.common.util.Time;
import org.keycloak.credential.CredentialModel;
import org.keycloak.util.JsonSerialization;

import edu.uw.waverify.authenticator.pin.credential.dto.PinCredentialData;
import edu.uw.waverify.authenticator.pin.credential.dto.PinSecretData;

public
class PinCredentialModel extends CredentialModel {

	public static final String TYPE = "PIN";

	private final PinCredentialData credentialData;
	private final PinSecretData     secretData;

	private
	PinCredentialModel( PinCredentialData credentialData, PinSecretData secretData ) {

		this.credentialData = credentialData;
		this.secretData = secretData;
	}

	private
	PinCredentialModel( String question, String answer ) {

		credentialData = new PinCredentialData( question );
		secretData = new PinSecretData( answer );
	}

	public static
	PinCredentialModel createFromCredentialModel( CredentialModel credentialModel ) {

		try {
			PinCredentialData credentialData = JsonSerialization.readValue( credentialModel.getCredentialData( ), PinCredentialData.class );
			PinSecretData     secretData     = JsonSerialization.readValue( credentialModel.getSecretData( ), PinSecretData.class );

			PinCredentialModel PinCredentialModel = new PinCredentialModel( credentialData, secretData );
			PinCredentialModel.setUserLabel( credentialModel.getUserLabel( ) );
			PinCredentialModel.setCreatedDate( credentialModel.getCreatedDate( ) );
			PinCredentialModel.setType( TYPE );
			PinCredentialModel.setId( credentialModel.getId( ) );
			PinCredentialModel.setSecretData( credentialModel.getSecretData( ) );
			PinCredentialModel.setCredentialData( credentialModel.getCredentialData( ) );
			return PinCredentialModel;
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public static
	PinCredentialModel createPin( String question, String answer ) {

		PinCredentialModel credentialModel = new PinCredentialModel( question, answer );
		credentialModel.fillCredentialModelFields( );
		return credentialModel;
	}

	private
	void fillCredentialModelFields( ) {

		try {
			setCredentialData( JsonSerialization.writeValueAsString( credentialData ) );
			setSecretData( JsonSerialization.writeValueAsString( secretData ) );
			setType( TYPE );
			setCreatedDate( Time.currentTimeMillis( ) );
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	public
	PinCredentialData getPinCredentialData( ) {

		return credentialData;
	}

	public
	PinSecretData getPinSecretData( ) {

		return secretData;
	}

}
