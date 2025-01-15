package edu.uw.waverify.spi.demographic.verification;

import java.util.Map;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DemographicVerificationServiceImplTest {

	@Mock
	private Client mockClient;

	@Mock
	private WebTarget mockTarget;

	@Mock
	private Invocation.Builder mockBuilder;

	@Mock
	private Response mockResponse;

	private DemographicVerificationServiceImpl service;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		service = new DemographicVerificationServiceImpl( );
		service.setClient( mockClient );
	}

	@Test
	void testVerify_InvalidDemographics_EmptyDemographics( ) {
		// Act
		boolean result = service.verify( "12345", Map.of( ) );

		// Assert
		assertFalse( result, "Demographic verification should return false for empty demographics." );
	}

	@Test
	void testVerify_InvalidDemographics_NullDemographics( ) {
		// Act
		boolean result = service.verify( "12345", null );

		// Assert
		assertFalse( result, "Demographic verification should return false for null demographics." );
	}

	@Test
	void testVerify_ServerErrorResponse( ) {
		// Arrange
		String                userId       = "12345";
		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );

		when( mockClient.target( DemographicVerificationServiceImpl.MOCK_VP_SERVER_URL ) ).thenReturn( mockTarget );
		when( mockTarget.request( "application/json" ) ).thenReturn( mockBuilder );
		when( mockBuilder.post( any( ) ) ).thenReturn( mockResponse );
		when( mockResponse.getStatus( ) ).thenReturn( 500 );

		// Act
		boolean result = service.verify( userId, demographics );

		// Assert
		assertFalse( result, "Demographic verification should return false for server errors." );
	}

	@Test
	void testVerify_UnexpectedResponseFormat( ) {
		// Arrange
		String                userId       = "12345";
		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );
		String                responseBody = "{\"unexpectedKey\":true}";

		when( mockClient.target( DemographicVerificationServiceImpl.MOCK_VP_SERVER_URL ) ).thenReturn( mockTarget );
		when( mockTarget.request( "application/json" ) ).thenReturn( mockBuilder );
		when( mockBuilder.post( any( ) ) ).thenReturn( mockResponse );
		when( mockResponse.getStatus( ) ).thenReturn( 200 );
		when( mockResponse.readEntity( String.class ) ).thenReturn( responseBody );

		// Act
		boolean result = service.verify( userId, demographics );

		// Assert
		assertFalse( result, "Demographic verification should return false for unexpected response format." );
	}

	@Test
	void testVerify_ValidDemographics_SuccessfulResponse( ) {
		// Arrange
		String                userId       = "12345";
		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );
		String                requestBody  = DemographicDataCodec.encode( userId, demographics );
		String                responseBody = "{\"valid\":true}";

		when( mockClient.target( DemographicVerificationServiceImpl.MOCK_VP_SERVER_URL ) ).thenReturn( mockTarget );
		when( mockTarget.request( "application/json" ) ).thenReturn( mockBuilder );
		when( mockBuilder.post( any( ) ) ).thenReturn( mockResponse );
		when( mockResponse.getStatus( ) ).thenReturn( 200 );
		when( mockResponse.readEntity( String.class ) ).thenReturn( responseBody );

		System.out.println( "Request body: " + requestBody );
		System.out.println( "Mocked response body: " + responseBody );

		// Act
		boolean result = service.verify( userId, demographics );

		// Assert
		System.out.println( "Verification result: " + result );
		assertTrue( result, "Demographic verification should return true for valid input." );
		verify( mockBuilder ).post( eq( Entity.entity( requestBody, "application/json" ) ) );
	}

}
