package edu.uw.waverify.pin;

import org.keycloak.common.util.Time;
import org.keycloak.credential.*;
import org.keycloak.models.*;

import edu.uw.waverify.pin.credential.PinCredentialModel;

import lombok.extern.jbosslog.JBossLog;

/**
 * Credential provider for PIN-based authentication.
 * <p>
 * This provider handles the creation, validation, and management of PIN credentials for Keycloak users.
 * </p>
 */
@JBossLog
public
class PinCredentialProvider implements CredentialProvider< PinCredentialModel >, CredentialInputValidator {

	protected KeycloakSession session;

	/**
	 * Constructs a new {@code PinCredentialProvider}.
	 *
	 * @param session
	 * 		the Keycloak session.
	 */
	public
	PinCredentialProvider( KeycloakSession session ) {

		this.session = session;
	}

	/**
	 * Retrieves the credential type handled by this provider.
	 *
	 * @return the credential type identifier.
	 */
	@Override
	public
	String getType( ) {

		return PinCredentialModel.TYPE;
	}

	/**
	 * Creates and stores a new PIN credential for a user.
	 *
	 * @param realm
	 * 		the Keycloak realm.
	 * @param user
	 * 		the user for whom the credential is created.
	 * @param credentialModel
	 * 		the PIN credential model.
	 *
	 * @return the stored credential model.
	 */
	@Override
	public
	CredentialModel createCredential( RealmModel realm, UserModel user, PinCredentialModel credentialModel ) {

		if ( credentialModel.getCreatedDate( ) == null ) {
			credentialModel.setCreatedDate( Time.currentTimeMillis( ) );
		}
		return user.credentialManager( )
		           .createStoredCredential( credentialModel );
	}

	/**
	 * Deletes a stored PIN credential for a user.
	 *
	 * @param realm
	 * 		the Keycloak realm.
	 * @param user
	 * 		the user whose credential is to be deleted.
	 * @param credentialId
	 * 		the ID of the credential to delete.
	 *
	 * @return {@code true} if the credential was deleted, otherwise {@code false}.
	 */
	@Override
	public
	boolean deleteCredential( RealmModel realm, UserModel user, String credentialId ) {

		return user.credentialManager( )
		           .removeStoredCredentialById( credentialId );
	}

	/**
	 * Converts a stored credential model into a {@code PinCredentialModel}.
	 *
	 * @param model
	 * 		the stored credential model.
	 *
	 * @return a {@code PinCredentialModel} instance.
	 */
	@Override
	public
	PinCredentialModel getCredentialFromModel( CredentialModel model ) {

		return PinCredentialModel.createFromCredentialModel( model );
	}

	/**
	 * Retrieves metadata describing the PIN credential type.
	 *
	 * @param metadataContext
	 * 		the context for credential metadata.
	 *
	 * @return metadata about the PIN credential type.
	 */
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

	/**
	 * Checks whether this provider supports a given credential type.
	 *
	 * @param credentialType
	 * 		the credential type.
	 *
	 * @return {@code true} if the type is supported, otherwise {@code false}.
	 */
	@Override
	public
	boolean supportsCredentialType( String credentialType ) {

		return getType( ).equals( credentialType );
	}

	/**
	 * Determines whether a user has a configured PIN credential.
	 *
	 * @param realm
	 * 		the Keycloak realm.
	 * @param user
	 * 		the user to check.
	 * @param credentialType
	 * 		the credential type.
	 *
	 * @return {@code true} if the user has a stored PIN credential, otherwise {@code false}.
	 */
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

	/**
	 * Validates a PIN credential input.
	 *
	 * @param realm
	 * 		the Keycloak realm.
	 * @param user
	 * 		the user to validate.
	 * @param input
	 * 		the credential input.
	 *
	 * @return {@code true} if the input is valid, otherwise {@code false}.
	 */
	@Override
	public
	boolean isValid( RealmModel realm, UserModel user, CredentialInput input ) {

		if ( !( input instanceof UserCredentialModel ) ) {
			log.warn( "Expected instance of UserCredentialModel for CredentialInput" );
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

	/**
	 * Updates an existing PIN credential for a user.
	 *
	 * @param user
	 * 		the user whose credential is to be updated.
	 * @param pinCredentialModel
	 * 		the new PIN credential model.
	 * @param pin
	 * 		the updated PIN value.
	 */
	public
	void updateCredential( UserModel user, PinCredentialModel pinCredentialModel, String pin ) {

		if ( pinCredentialModel.getCreatedDate( ) == null ) {
			pinCredentialModel.setCreatedDate( Time.currentTimeMillis( ) );
		}
		user.credentialManager( )
		    .updateStoredCredential( pinCredentialModel );
	}

}
