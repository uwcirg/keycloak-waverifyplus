package edu.uw.waverify.demographic.authenticator.verification;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.*;

/**
 * Utility class for encoding and decoding demographic data into JSON. Encapsulates JSON serialization and
 * deserialization logic for separation of concerns.
 */
public
class DemographicDataCodec {

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

		try ( JsonReader jsonReader = Json.createReader( new StringReader( json ) ) ) {
			JsonObject            jsonObject = jsonReader.readObject( );
			Map< String, Object > result     = new HashMap<>( );

			var valid = jsonObject.getBoolean( "valid" );
			result.put( "valid", valid );

			return result;
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

		JsonObjectBuilder builder = Json.createObjectBuilder( );

		demographics.forEach( builder::add );

		JsonObject jsonObject = builder.build( );

		StringWriter stringWriter = new StringWriter( );
		try ( JsonWriter jsonWriter = Json.createWriter( stringWriter ) ) {
			jsonWriter.writeObject( jsonObject );
		}
		return stringWriter.toString( );
	}

}
