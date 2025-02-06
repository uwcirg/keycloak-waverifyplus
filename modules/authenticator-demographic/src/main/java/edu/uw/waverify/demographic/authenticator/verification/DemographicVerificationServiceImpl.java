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
 * Implementation of the {@link DemographicVerificationService} interface.
 * <p>
 * This service verifies demographic information by sending it to the mock-vp server using Keycloak's {@link SimpleHttp}
 * utility. It encodes the demographic data into JSON, sends an HTTP POST request, and processes the response to
 * determine validity.
 * </p>
 */
@Setter
@Getter
@JBossLog
public
class DemographicVerificationServiceImpl implements DemographicVerificationService {

	private final KeycloakSession session;
	private       String          baseUrl;

	/**
	 * Constructs a new instance of {@code DemographicVerificationServiceImpl}.
	 *
	 * @param session
	 * 		the Keycloak session used for HTTP requests.
	 * @param baseUrl
	 * 		the base URL of the verification service.
	 */
	public
	DemographicVerificationServiceImpl( KeycloakSession session, String baseUrl ) {

		this.session = session;
		this.baseUrl = baseUrl;
	}

	/**
	 * Verifies the provided demographic information by sending it to the mock-vp server.
	 *
	 * @param demographics
	 * 		a map containing demographic data where keys represent attribute names and values represent corresponding
	 * 		user-provided values.
	 *
	 * @return {@code true} if the demographic information is valid, otherwise {@code false}.
	 */
	@Override
	public
	boolean verify( Map< String, String > demographics ) {

		try {
			var requestBody = DemographicDataCodec.encode( demographics );

			var responseBody = SimpleHttp.doPost( baseUrl, session )
			                             .header( "Content-Type", "application/json" )
			                             .entity( new StringEntity( requestBody, APPLICATION_JSON ) )
			                             .asString( );

			var decodedResponse = DemographicDataCodec.decode( responseBody );

			return Boolean.TRUE.equals( decodedResponse.get( "valid" ) );
		} catch ( Exception e ) {
			log.error( "Error during demographic verification: " + e.getMessage( ), e );
		}
		return false;
	}

}
