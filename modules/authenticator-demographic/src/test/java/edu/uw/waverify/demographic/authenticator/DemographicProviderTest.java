package edu.uw.waverify.demographic.authenticator;

import java.util.HashMap;

import org.keycloak.models.KeycloakSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DemographicAuthenticator implementation.
 */
class DemographicProviderTest {

	@Mock
	private KeycloakSession mockSession;

	private DemographicAuthenticatorImpl demographicAuthenticator;

	/**
	 * Sets up the test environment by initializing the DemographicAuthenticator with a mock session and a mock
	 * verification service.
	 */
	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		var verificationService = new MockDemographicVerificationService( );
		var providerFactory     = new DemographicAuthenticatorFactory( );
		demographicAuthenticator = ( DemographicAuthenticatorImpl ) providerFactory.create( mockSession );
		demographicAuthenticator.setVerificationService( verificationService );
	}

	/**
	 * Tests the validation logic when demographic data is empty.
	 */
	@Test
	void testValidateDemographics_EmptyData( ) {

		var demographics = new HashMap< String, String >( );

		var result = demographicAuthenticator.validateDemographics( demographics );

		assertFalse( result, "Expected demographics to be invalid due to empty data" );
	}

	/**
	 * Tests the validation logic when demographic data is incomplete.
	 */
	@Test
	void testValidateDemographics_InvalidData( ) {

		var demographics = new HashMap< String, String >( );
		demographics.put( "firstName", "John" );

		var result = demographicAuthenticator.validateDemographics( demographics );

		assertFalse( result, "Expected demographics to be invalid due to missing data" );
	}

	/**
	 * Tests the validation logic when demographic data is complete and valid.
	 */
	@Test
	void testValidateDemographics_ValidData( ) {

		var demographics = new HashMap< String, String >( );
		demographics.put( "firstName", "John" );
		demographics.put( "lastName", "Doe" );

		boolean result = demographicAuthenticator.validateDemographics( demographics );

		assertTrue( result, "Expected demographics to be valid" );
	}

}
