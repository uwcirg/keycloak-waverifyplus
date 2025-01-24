package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

/**
 * Implementation of the DemographicVerificationService interface. This class provides the logic for verifying
 * demographic information by interacting with the mock-vp server using jakarta.ws.rs.client.
 */
@Setter
@Getter
public
class DemographicVerificationServiceImpl implements DemographicVerificationService, AutoCloseable {

	private Client client;
	public  String baseUrl = "http://mock-vp-server/verify";

	/**
	 * Default constructor initializing the JAX-RS client.
	 */
	public
	DemographicVerificationServiceImpl( ) {

	}

	/**
	 * Closes the JAX-RS client.
	 */
	@Override
	public
	void close( ) {

		client.close( );
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

			Response response = getClient( ).target( baseUrl )
			                                .request( MediaType.APPLICATION_JSON )
			                                .post( Entity.entity( requestBody, MediaType.APPLICATION_JSON ) );

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

	private
	Client getClient( ) {

		if ( this.client == null ) {
			this.client = ClientBuilder.newClient( );
		}
		return this.client;
	}

}
