package edu.uw.waverify.pin;

import org.keycloak.common.util.Time;
import org.keycloak.credential.*;
import org.keycloak.models.*;

import edu.uw.waverify.pin.credential.PinCredentialModel;

import org.jboss.logging.Logger;

public
class PinCredentialProvider implements CredentialProvider< PinCredentialModel >, CredentialInputValidator {

	private static final Logger logger = Logger.getLogger( PinCredentialProvider.class );

	protected KeycloakSession session;

	public
	PinCredentialProvider( KeycloakSession session ) {

		this.session = session;
	}

	@Override
	public
	String getType( ) {

		return PinCredentialModel.TYPE;
	}

	@Override
	public
	CredentialModel createCredential( RealmModel realm, UserModel user, PinCredentialModel credentialModel ) {

		if ( credentialModel.getCreatedDate( ) == null ) {
			credentialModel.setCreatedDate( Time.currentTimeMillis( ) );
		}
		return user.credentialManager( )
		           .createStoredCredential( credentialModel );
	}

	@Override
	public
	boolean deleteCredential( RealmModel realm, UserModel user, String credentialId ) {

		return user.credentialManager( )
		           .removeStoredCredentialById( credentialId );
	}

	@Override
	public
	PinCredentialModel getCredentialFromModel( CredentialModel model ) {

		return PinCredentialModel.createFromCredentialModel( model );
	}

	@Override
	public
	CredentialTypeMetadata getCredentialTypeMetadata( CredentialTypeMetadataContext metadataContext ) {

		return CredentialTypeMetadata.builder( )
		                             .type( getType( ) )
		                             .category( CredentialTypeMetadata.Category.BASIC_AUTHENTICATION )
		                             .displayName( PinCredentialProviderFactory.PROVIDER_ID )
		                             .helpText( "pin-text" )
		                             .createAction( PinAuthenticatorFactory.PROVIDER_ID )
		                             .removeable( false )
		                             .build( session );
	}

	@Override
	public
	boolean supportsCredentialType( String credentialType ) {

		return getType( ).equals( credentialType );
	}

	@Override
	public
	boolean isConfiguredFor( RealmModel realm, UserModel user, String credentialType ) {

		if ( !supportsCredentialType( credentialType ) ) {
			return false;
		}
		return user.credentialManager( )
		           .getStoredCredentialsByTypeStream( credentialType )
		           .findAny( )
		           .isPresent( );
	}

	@Override
	public
	boolean isValid( RealmModel realm, UserModel user, CredentialInput input ) {

		if ( !( input instanceof UserCredentialModel ) ) {
			logger.debug( "Expected instance of UserCredentialModel for CredentialInput" );
			return false;
		}
		if ( !input.getType( )
		           .equals( getType( ) ) ) {
			return false;
		}
		String challengeResponse = input.getChallengeResponse( );
		if ( challengeResponse == null ) {
			return false;
		}
		CredentialModel credentialModel = user.credentialManager( )
		                                      .getStoredCredentialById( input.getCredentialId( ) );
		PinCredentialModel sqcm = getCredentialFromModel( credentialModel );
		return sqcm.getPinSecretData( )
		           .getPin( )
		           .equals( challengeResponse );
	}

}
