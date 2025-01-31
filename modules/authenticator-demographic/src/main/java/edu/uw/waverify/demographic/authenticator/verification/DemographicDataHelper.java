package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Optional;

import org.keycloak.http.HttpRequest;
import org.keycloak.models.*;
import org.keycloak.sessions.AuthenticationSessionModel;

/**
 * Helper class for extracting, validating, and storing demographic data in authentication workflows.
 */
public
class DemographicDataHelper {

	/**
	 * Extracts demographic data from an incoming HTTP request.
	 *
	 * @param request
	 * 		the HTTP request containing form parameters.
	 *
	 * @return a {@link DemographicData} object populated with the extracted values.
	 */
	public static
	DemographicData extractFromRequest( HttpRequest request ) {

		var formData = request.getDecodedFormParameters( );

		var firstName   = formData.getFirst( "firstName" );
		var lastName    = formData.getFirst( "lastName" );
		var dateOfBirth = formData.getFirst( "dateOfBirth" );
		var email       = formData.getFirst( "email" );
		var pin         = formData.getFirst( "pin" );

		return new DemographicData( firstName, lastName, dateOfBirth, email, pin );
	}

	/**
	 * Extracts demographic data from an authentication session.
	 *
	 * @param authSession
	 * 		the authentication session containing stored user attributes.
	 *
	 * @return a {@link DemographicData} object populated with session-stored values.
	 */
	public static
	DemographicData extractFromSession( AuthenticationSessionModel authSession ) {

		var firstName   = authSession.getAuthNote( "firstName" );
		var lastName    = authSession.getAuthNote( "lastName" );
		var dateOfBirth = authSession.getAuthNote( "dateOfBirth" );
		var email       = authSession.getAuthNote( "email" );
		var pin         = authSession.getAuthNote( "pin" );

		return new DemographicData( firstName, lastName, dateOfBirth, email, pin );
	}

	/**
	 * Checks if a string is non-null and not blank.
	 *
	 * @param value
	 * 		the string to check.
	 *
	 * @return {@code true} if the string is non-null and not blank, otherwise {@code false}.
	 */
	private static
	boolean isNotBlank( String value ) {

		return value != null && !value.isBlank( );
	}

	/**
	 * Validates the given demographic data.
	 *
	 * @param data
	 * 		the demographic data to validate.
	 *
	 * @return {@code true} if the demographic data is valid, otherwise {@code false}.
	 */
	public static
	boolean isValid( DemographicData data ) {

		return Optional.ofNullable( data )
		               .map( d -> isNotBlank( d.getFirstName( ) ) && isNotBlank( d.getLastName( ) ) && isNotBlank( d.getDateOfBirth( ) ) && isNotBlank( d.getEmail( ) ) && isNotBlank( d.getPin( ) ) )
		               .orElse( false );
	}

	/**
	 * Creates or retrieves a user based on demographic data and adds them to the authentication context.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param realm
	 * 		the Keycloak realm.
	 * @param authSession
	 * 		the authentication session containing demographic attributes.
	 *
	 * @return the created or retrieved {@link UserModel}.
	 */
	public static
	UserModel saveUser( KeycloakSession session, RealmModel realm, AuthenticationSessionModel authSession ) {

		var email = authSession.getAuthNote( "email" );

		if ( email == null || email.isBlank( ) ) {
			throw new IllegalArgumentException( "Email is required for user registration." );
		}

		var userProvider = session.users( );
		var user         = userProvider.getUserByEmail( realm, email );

		if ( user == null ) {
			user = userProvider.addUser( realm, email );
			user.setEnabled( true );
			user.setEmail( email );
			user.setEmailVerified( true );
		}

		var firstName   = authSession.getAuthNote( "firstName" );
		var lastName    = authSession.getAuthNote( "lastName" );
		var dateOfBirth = authSession.getAuthNote( "dateOfBirth" );
		var pin         = authSession.getAuthNote( "pin" );

		user.setFirstName( firstName );
		user.setLastName( lastName );
		user.setSingleAttribute( "dateOfBirth", dateOfBirth );

		return user;
	}

	/**
	 * Stores demographic data in the authentication session.
	 *
	 * @param authSession
	 * 		the authentication session to store demographic attributes in.
	 * @param data
	 * 		the demographic data to store.
	 */
	public static
	void storeInAuthSession( AuthenticationSessionModel authSession, DemographicData data ) {

		authSession.setAuthNote( "firstName", data.getFirstName( ) );
		authSession.setAuthNote( "lastName", data.getLastName( ) );
		authSession.setAuthNote( "dateOfBirth", data.getDateOfBirth( ) );
		authSession.setAuthNote( "email", data.getEmail( ) );
		authSession.setAuthNote( "pin", data.getPin( ) );
	}

}
