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

class DemographicAuthenticatorImplTest {

	@Mock
	private KeycloakSession mockSession;

	@Mock
	private DemographicVerificationService mockVerificationService;

	private DemographicAuthenticatorImpl provider;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		provider = new DemographicAuthenticatorImpl( mockSession, mockVerificationService );
	}

	@Test
	void testClose_NoOp( ) {

		provider.close( );
	}

	@Test
	void testValidateDemographics_EmptyDemographics_ReturnsFalse( ) {

		boolean result = provider.validateDemographics( Map.of( ) );
		assertFalse( result, "Validation should return false for empty demographics" );
	}

	@Test
	void testValidateDemographics_MissingFirstName_ReturnsFalse( ) {

		Map< String, String > demographics = Map.of( "lastName", "Doe" );
		boolean               result       = provider.validateDemographics( demographics );
		assertFalse( result, "Validation should return false if firstName is missing" );
	}

	@Test
	void testValidateDemographics_MissingLastName_ReturnsFalse( ) {

		Map< String, String > demographics = Map.of( "firstName", "John" );
		boolean               result       = provider.validateDemographics( demographics );
		assertFalse( result, "Validation should return false if lastName is missing" );
	}

	@Test
	void testValidateDemographics_NullDemographics_ReturnsFalse( ) {

		boolean result = provider.validateDemographics( null );
		assertFalse( result, "Validation should return false for null demographics" );
	}

	@Test
	void testValidateDemographics_ValidDemographics_VerifiesUsingService( ) {

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );
		when( mockVerificationService.verify( demographics ) ).thenReturn( true );

		boolean result = provider.validateDemographics( demographics );

		assertTrue( result, "Validation should return true for valid demographics" );
		verify( mockVerificationService ).verify( demographics );
	}

}
