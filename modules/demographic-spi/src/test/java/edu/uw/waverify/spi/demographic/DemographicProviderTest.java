package edu.uw.waverify.spi.demographic;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.models.KeycloakSession;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DemographicProviderTest {

	@Mock
	private KeycloakSession mockSession;

	private DemographicProvider demographicProvider;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		var verificationService = new MockDemographicVerificationService( );
		var providerFactory     = new DemographicProviderFactory( verificationService );
		demographicProvider = providerFactory.create( mockSession );
	}

	@Test
	void testValidateDemographics_EmptyData( ) {

		var userId       = "user123";
		var demographics = new HashMap< String, String >( );

		var result = demographicProvider.validateDemographics( userId, demographics );

		assertFalse( result, "Expected demographics to be invalid due to empty data" );
	}

	@Test
	void testValidateDemographics_InvalidData( ) {

		var userId       = "user123";
		var demographics = new HashMap< String, String >( );
		demographics.put( "firstName", "John" );

		var result = demographicProvider.validateDemographics( userId, demographics );

		assertFalse( result, "Expected demographics to be invalid due to missing data" );
	}

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
