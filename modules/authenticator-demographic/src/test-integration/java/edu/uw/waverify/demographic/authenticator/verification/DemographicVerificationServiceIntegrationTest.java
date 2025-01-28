package edu.uw.waverify.demographic.authenticator.verification;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.mvp.MockVpApplication;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
		classes = MockVpApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class DemographicVerificationServiceIntegrationTest {

	@LocalServerPort
	private int port;

	private DemographicVerificationServiceImpl service;

	@BeforeEach
	void setUp( ) {

		KeycloakSession mockSession = Mockito.mock( KeycloakSession.class );

		CloseableHttpClient httpClient = HttpClients.createDefault( );

		HttpClientProvider realHttpClientProvider = new HttpClientProvider( ) {
			@Override
			public
			void close( ) {

			}

			@Override
			public
			CloseableHttpClient getHttpClient( ) {

				return httpClient;
			}

			@Override
			public
			int postText( String s, String s1 ) throws IOException {

				return 0;
			}

			@Override
			public
			String getString( String s ) throws IOException {

				return "";
			}

			@Override
			public
			InputStream getInputStream( String url ) {

				throw new UnsupportedOperationException( "getInputStream is not implemented for testing" );
			}
		};

		Mockito.when( mockSession.getProvider( HttpClientProvider.class ) )
		       .thenReturn( realHttpClientProvider );

		var baseUrl = "http://localhost:" + port + "/api/validation";
		service = new DemographicVerificationServiceImpl( mockSession, baseUrl );
	}


	@Test
	void testInvalidDemographics_BlankName( ) {

		Map< String, String > demographics = Map.of( "firstName", "", "lastName", "Doe", "dob", "1990-01-01" );

		boolean result = service.verify( demographics );

		assertFalse( result, "Expected invalid demographics to return false due to blank first name" );
	}

	@Test
	void testInvalidDemographics_FutureDOB( ) {

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe", "dob", "2100-01-01" );

		boolean result = service.verify( demographics );

		assertFalse( result, "Expected invalid demographics to return false due to future DOB" );
	}

	@Test
	void testValidDemographics( ) {

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe", "dob", "1990-01-01" );

		boolean result = service.verify( demographics );

		assertTrue( result, "Expected valid demographics to return true" );
	}

}
