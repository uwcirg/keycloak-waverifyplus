package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;
import org.apache.http.entity.StringEntity;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * Implementation of the DemographicVerificationService interface. This class provides the logic for verifying
 * demographic information by interacting with the mock-vp server using Keycloak's SimpleHttp utility.
 */
@Setter
@Getter
@JBossLog
public
class DemographicVerificationServiceImpl implements DemographicVerificationService {

	private final KeycloakSession session;
	private       String          baseUrl;

	/**
	 * Constructor initializing the service with a Keycloak session.
	 *
	 * @param session
	 * 		the Keycloak session, used to configure HTTP requests.
	 */
	public
	DemographicVerificationServiceImpl( KeycloakSession session, String baseUrl ) {

		this.session = session;
		this.baseUrl = baseUrl;
	}

	/**
	 * Verifies the provided demographic information for a given user ID by sending it to the mock-vp server.
	 *
	 * @param demographics
	 * 		A map containing demographic data where keys represent the attribute names and values represent their
	 * 		corresponding values.
	 *
	 * @return {@code true} if the demographic information is valid, {@code false} otherwise.
	 */
	@Override
	public
	boolean verify( Map< String, String > demographics ) {

		try {
			String requestBody = DemographicDataCodec.encode( demographics );

			String responseBody = SimpleHttp.doPost( baseUrl, session )
			                                .header( "Content-Type", "application/json" )
			                                .entity( new StringEntity( requestBody, APPLICATION_JSON ) )
			                                .asString( );

			Map< String, Object > decodedResponse = DemographicDataCodec.decode( responseBody );

			return Boolean.TRUE.equals( decodedResponse.get( "valid" ) );

		} catch ( Exception e ) {
			log.error( "Error during demographic verification: " + e.getMessage( ) );
		}
		return false;
	}

}
