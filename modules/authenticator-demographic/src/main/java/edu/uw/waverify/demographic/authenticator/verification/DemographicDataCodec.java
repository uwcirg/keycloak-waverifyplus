package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for encoding and decoding demographic data into JSON. Encapsulates JSON serialization and
 * deserialization logic for separation of concerns.
 */
public
class DemographicDataCodec {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper( );

	/**
	 * Decodes a JSON string into a map containing the "userId" and "demographics".
	 *
	 * @param json
	 * 		The JSON string to decode.
	 *
	 * @return A map containing the decoded "userId" and "demographics".
	 */
	public static
	Map< String, Object > decode( String json ) {

		try {
			return OBJECT_MAPPER.readValue( json, new TypeReference< Map< String, Object > >( ) { } );
		} catch ( Exception e ) {
			throw new RuntimeException( "Failed to decode JSON string.", e );
		}
	}

	/**
	 * Encodes demographic data and user ID into a JSON string.
	 *
	 * @param demographics
	 * 		The demographic data to encode.
	 *
	 * @return A JSON string representing the encoded data.
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
