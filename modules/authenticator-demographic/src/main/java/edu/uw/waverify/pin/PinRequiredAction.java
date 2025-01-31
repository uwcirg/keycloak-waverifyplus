package edu.uw.waverify.pin;

import org.keycloak.authentication.*;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.sessions.AuthenticationSessionModel;

import edu.uw.waverify.pin.credential.PinCredentialModel;

import jakarta.ws.rs.core.Response;

import static edu.uw.waverify.pin.credential.PinCredentialModel.TYPE;

public
class PinRequiredAction implements RequiredActionProvider, CredentialRegistrator {

	public static final String PROVIDER_ID = "pin_config";

	@Override
	public
	void close( ) {

	}

	@Override
	public
	void evaluateTriggers( RequiredActionContext context ) {

	}

	@Override
	public
	void requiredActionChallenge( RequiredActionContext context ) {

		Response challenge = context.form( )
		                            .createForm( "pin-config.ftl" );
		context.challenge( challenge );

	}

	@Override
	public
	void processAction( RequiredActionContext context ) {

		var pin = ( context.getHttpRequest( )
		                   .getDecodedFormParameters( )
		                   .getFirst( "pin" ) );
		PinCredentialProvider sqcp = ( PinCredentialProvider ) context.getSession( )
		                                                              .getProvider( CredentialProvider.class, TYPE );
		sqcp.createCredential( context.getRealm( ), context.getUser( ), PinCredentialModel.createPin( pin ) );
		context.success( );
	}

	@Override
	public
	String getCredentialType( KeycloakSession session, AuthenticationSessionModel AuthenticationSession ) {

		return TYPE;
	}

}
