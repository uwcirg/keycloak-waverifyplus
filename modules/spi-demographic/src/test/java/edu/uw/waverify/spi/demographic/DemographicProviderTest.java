package edu.uw.waverify.spi.demographic;

import java.util.HashMap;

import org.keycloak.models.KeycloakSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DemographicProvider implementation.
 */
class DemographicProviderTest {

	@Mock
	private KeycloakSession mockSession;

	private DemographicProvider demographicProvider;

	/**
	 * Sets up the test environment by initializing the DemographicProvider with a mock session and a mock verification
	 * service.
	 */
	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		var verificationService = new MockDemographicVerificationService( );
		var providerFactory     = new DemographicProviderFactory( );
		providerFactory.setDemographicVerificationService( verificationService );
		demographicProvider = providerFactory.create( mockSession );
	}

	/**
	 * Tests the validation logic when demographic data is empty.
	 */
	@Test
	void testValidateDemographics_EmptyData( ) {

		var userId       = "user123";
		var demographics = new HashMap< String, String >( );

		var result = demographicProvider.validateDemographics( userId, demographics );

		assertFalse( result, "Expected demographics to be invalid due to empty data" );
	}

	/**
	 * Tests the validation logic when demographic data is incomplete.
	 */
	@Test
	void testValidateDemographics_InvalidData( ) {

		var userId       = "user123";
		var demographics = new HashMap< String, String >( );
		demographics.put( "firstName", "John" );

		var result = demographicProvider.validateDemographics( userId, demographics );

		assertFalse( result, "Expected demographics to be invalid due to missing data" );
	}

	/**
	 * Tests the validation logic when demographic data is complete and valid.
	 */
	@Test
	void testValidateDemographics_ValidData( ) {

		var userId       = "user123";
		var demographics = new HashMap< String, String >( );
		demographics.put( "firstName", "John" );
		demographics.put( "lastName", "Doe" );

		boolean result = demographicProvider.validateDemographics( userId, demographics );

		assertTrue( result, "Expected demographics to be valid" );
	}

}
