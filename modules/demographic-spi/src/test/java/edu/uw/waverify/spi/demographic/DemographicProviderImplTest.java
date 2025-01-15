package edu.uw.waverify.spi.demographic;

import java.util.Map;

import org.keycloak.models.KeycloakSession;

import edu.uw.waverify.spi.demographic.verification.DemographicVerificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DemographicProviderImplTest {

	@Mock
	private KeycloakSession mockSession;

	@Mock
	private DemographicVerificationService mockVerificationService;

	private DemographicProviderImpl provider;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		provider = new DemographicProviderImpl( mockSession, mockVerificationService );
	}

	@Test
	void testClose_NoOp( ) {

		provider.close( );
	}

	@Test
	void testValidateDemographics_EmptyDemographics_ReturnsFalse( ) {

		boolean result = provider.validateDemographics( "user123", Map.of( ) );
		assertFalse( result, "Validation should return false for empty demographics" );
	}

	@Test
	void testValidateDemographics_MissingFirstName_ReturnsFalse( ) {

		Map< String, String > demographics = Map.of( "lastName", "Doe" );
		boolean               result       = provider.validateDemographics( "user123", demographics );
		assertFalse( result, "Validation should return false if firstName is missing" );
	}

	@Test
	void testValidateDemographics_MissingLastName_ReturnsFalse( ) {

		Map< String, String > demographics = Map.of( "firstName", "John" );
		boolean               result       = provider.validateDemographics( "user123", demographics );
		assertFalse( result, "Validation should return false if lastName is missing" );
	}

	@Test
	void testValidateDemographics_NullDemographics_ReturnsFalse( ) {

		boolean result = provider.validateDemographics( "user123", null );
		assertFalse( result, "Validation should return false for null demographics" );
	}

	@Test
	void testValidateDemographics_ValidDemographics_VerifiesUsingService( ) {

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );
		when( mockVerificationService.verify( "user123", demographics ) ).thenReturn( true );

		boolean result = provider.validateDemographics( "user123", demographics );

		assertTrue( result, "Validation should return true for valid demographics" );
		verify( mockVerificationService ).verify( "user123", demographics );
	}

}
