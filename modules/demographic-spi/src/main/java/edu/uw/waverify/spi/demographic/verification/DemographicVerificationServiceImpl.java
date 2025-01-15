package edu.uw.waverify.spi.demographic.verification;

import java.util.Map;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Setter;

/**
 * Implementation of the DemographicVerificationService interface. This class provides the logic for verifying
 * demographic information by interacting with the mock-vp server using jakarta.ws.rs.client.
 */
public
class DemographicVerificationServiceImpl implements DemographicVerificationService {

	static final String MOCK_VP_SERVER_URL = "http://mock-vp-server/verify";

	@Setter
	private Client client;

	/**
	 * Default constructor initializing the JAX-RS client.
	 */
	public
	DemographicVerificationServiceImpl( ) {

		this.client = ClientBuilder.newClient( );
	}

	/**
	 * Verifies the provided demographic information for a given user ID by sending it to the mock-vp server.
	 *
	 * @param userId
	 * 		The ID of the user whose demographics are being verified.
	 * @param demographics
	 * 		A map containing demographic data where keys represent the attribute names and values represent their
	 * 		corresponding values.
	 *
	 * @return {@code true} if the demographic information is valid, {@code false} otherwise.
	 */
	@Override
	public
	boolean verify( String userId, Map< String, String > demographics ) {

		if ( demographics == null || demographics.isEmpty( ) ) {
			return false;
		}

		try {
			// Encode the demographic data to JSON
			String requestBody = DemographicDataCodec.encode( userId, demographics );

			// Send POST request to the mock-vp server
			Response response = client.target( MOCK_VP_SERVER_URL )
			                          .request( MediaType.APPLICATION_JSON )
			                          .post( Entity.entity( requestBody, MediaType.APPLICATION_JSON ) );

			// Handle the response
			if ( response.getStatus( ) == 200 ) {
				String                responseBody    = response.readEntity( String.class );
				Map< String, Object > decodedResponse = DemographicDataCodec.decode( responseBody );
				return Boolean.TRUE.equals( decodedResponse.get( "valid" ) );
			} else {
				System.err.println( "Error: Received status code " + response.getStatus( ) );
			}
		} catch ( Exception e ) {
			System.err.println( "Error during demographic verification: " + e.getMessage( ) );
		}

		return false;
	}

	/**
	 * Closes the JAX-RS client.
	 */
	public
	void close( ) {

		client.close( );
	}

}
