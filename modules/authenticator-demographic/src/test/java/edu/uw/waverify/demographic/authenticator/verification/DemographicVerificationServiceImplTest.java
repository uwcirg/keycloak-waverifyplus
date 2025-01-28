package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DemographicVerificationServiceImplTest {

	@Mock
	private KeycloakSession mockSession;

	@Mock
	private HttpClientProvider mockHttpClientProvider;

	@Mock
	private CloseableHttpClient mockCloseableHttpClient;

	private DemographicVerificationServiceImpl service;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		var baseUrl = "http://localhost:8080/api/validation";
		service = new DemographicVerificationServiceImpl( mockSession, baseUrl );
	}

	@Test
	void testVerify_InvalidDemographics_EmptyDemographics( ) {

		boolean result = service.verify( Map.of( ) );
		assertFalse( result );
	}

	@Test
	void testVerify_InvalidDemographics_NullDemographics( ) {

		boolean result = service.verify( null );
		assertFalse( result );
	}

	@Test
	void testVerify_ServerErrorResponse( ) throws Exception {
		// Setup HTTP mocks only for this test
		when( mockSession.getProvider( HttpClientProvider.class ) ).thenReturn( mockHttpClientProvider );
		when( mockHttpClientProvider.getHttpClient( ) ).thenReturn( mockCloseableHttpClient );

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );

		try ( MockedStatic< SimpleHttp > simpleHttpMock = Mockito.mockStatic( SimpleHttp.class ) ) {
			SimpleHttp mockSimpleHttp = mock( SimpleHttp.class );

			simpleHttpMock.when( ( ) -> SimpleHttp.doPost( any( ), eq( mockSession ) ) )
			              .thenReturn( mockSimpleHttp );

			when( mockSimpleHttp.header( anyString( ), anyString( ) ) ).thenReturn( mockSimpleHttp );
			when( mockSimpleHttp.json( anyString( ) ) ).thenReturn( mockSimpleHttp );
			when( mockSimpleHttp.asString( ) ).thenThrow( new RuntimeException( "Server error" ) );

			boolean result = service.verify( demographics );
			assertFalse( result );
		}
	}

	@Test
	void testVerify_UnexpectedResponseFormat( ) throws Exception {
		// Setup HTTP mocks only for this test
		when( mockSession.getProvider( HttpClientProvider.class ) ).thenReturn( mockHttpClientProvider );
		when( mockHttpClientProvider.getHttpClient( ) ).thenReturn( mockCloseableHttpClient );

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );

		try ( MockedStatic< SimpleHttp > simpleHttpMock = Mockito.mockStatic( SimpleHttp.class ) ) {
			SimpleHttp mockSimpleHttp = mock( SimpleHttp.class );

			simpleHttpMock.when( ( ) -> SimpleHttp.doPost( any( ), eq( mockSession ) ) )
			              .thenReturn( mockSimpleHttp );

			when( mockSimpleHttp.header( anyString( ), anyString( ) ) ).thenReturn( mockSimpleHttp );
			when( mockSimpleHttp.json( anyString( ) ) ).thenReturn( mockSimpleHttp );
			when( mockSimpleHttp.asString( ) ).thenReturn( "{\"unexpectedKey\":true}" );

			boolean result = service.verify( demographics );
			assertFalse( result );
		}
	}

	@Test
	void testVerify_ValidDemographics_SuccessfulResponse( ) throws Exception {
		// Setup HTTP mocks only for this test
		when( mockSession.getProvider( HttpClientProvider.class ) ).thenReturn( mockHttpClientProvider );
		when( mockHttpClientProvider.getHttpClient( ) ).thenReturn( mockCloseableHttpClient );

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe", "dob", "1990-01-01" );

		try ( MockedStatic< SimpleHttp > simpleHttpMock = Mockito.mockStatic( SimpleHttp.class ) ) {
			SimpleHttp mockSimpleHttp = mock( SimpleHttp.class );

			simpleHttpMock.when( ( ) -> SimpleHttp.doPost( any( ), eq( mockSession ) ) )
			              .thenReturn( mockSimpleHttp );

			when( mockSimpleHttp.header( eq( "Content-Type" ), eq( "application/json" ) ) ).thenReturn( mockSimpleHttp );
			when( mockSimpleHttp.json( contains( "\"firstName\":\"John\"" ) ) ).thenReturn( mockSimpleHttp );
			when( mockSimpleHttp.asString( ) ).thenReturn( "{\"valid\":true}" );

			boolean result = service.verify( demographics );
			assertTrue( result );

			verify( mockSimpleHttp ).json( argThat( ( String json ) -> json.contains( "John" ) && json.contains( "Doe" ) && json.contains( "1990-01-01" ) ) );
		}
	}

}
