package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for encoding and decoding demographic data into JSON.
 * <p>
 * This class provides methods for serializing and deserializing demographic data, ensuring separation of concerns
 * between data representation and business logic.
 * </p>
 */
public
class DemographicDataCodec {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper( );

	/**
	 * Decodes a JSON string into a map containing demographic data.
	 *
	 * @param json
	 * 		The JSON string to decode.
	 *
	 * @return A map representing the decoded demographic data.
	 *
	 * @throws RuntimeException
	 * 		if the decoding process fails.
	 */
	public static
	Map< String, Object > decode( String json ) {

		try {
			return OBJECT_MAPPER.readValue( json, new TypeReference<>( ) { } );
		} catch ( Exception e ) {
			throw new RuntimeException( "Failed to decode JSON string.", e );
		}
	}

	/**
	 * Encodes demographic data into a JSON string.
	 *
	 * @param demographics
	 * 		The demographic data to encode.
	 *
	 * @return A JSON string representing the encoded data.
	 *
	 * @throws RuntimeException
	 * 		if the encoding process fails.
	 */
	public static
	String encode( Map< String, String > demographics ) {

		try {
			return OBJECT_MAPPER.writeValueAsString( demographics );
		} catch ( Exception e ) {
			throw new RuntimeException( "Failed to encode demographic data.", e );
		}
	}

}
