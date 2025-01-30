package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Optional;

import org.keycloak.http.HttpRequest;
import org.keycloak.sessions.AuthenticationSessionModel;

/**
 * Helper class for extracting and validating demographic data from Keycloak authentication requests.
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

		return new DemographicData( formData.getFirst( "firstName" ), formData.getFirst( "lastName" ), formData.getFirst( "dob" ), formData.getFirst( "email" ), formData.getFirst( "phoneNumber" ) );
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

		return new DemographicData( authSession.getAuthNote( "firstName" ), authSession.getAuthNote( "lastName" ), authSession.getAuthNote( "dateOfBirth" ), authSession.getAuthNote( "email" ), authSession.getAuthNote( "phoneNumber" ) );
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
		               .map( d -> d.getFirstName( ) != null && !d.getFirstName( )
		                                                         .isBlank( ) && d.getLastName( ) != null && !d.getLastName( )
		                                                                                                      .isBlank( ) && d.getDateOfBirth( ) != null && !d.getDateOfBirth( )
		                                                                                                                                                      .isBlank( ) && d.getEmail( ) != null && !d.getEmail( )
		                                                                                                                                                                                                .isBlank( ) && d.getPhoneNumber( ) != null && !d.getPhoneNumber( )
		                                                                                                                                                                                                                                                .isBlank( ) )
		               .orElse( false );
	}

}
