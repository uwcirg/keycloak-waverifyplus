package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.mvp.MockVpApplication;

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

		service = new DemographicVerificationServiceImpl( mockSession );
		service.setBaseUrl( "http://localhost:" + port + "/api/validation" );
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
