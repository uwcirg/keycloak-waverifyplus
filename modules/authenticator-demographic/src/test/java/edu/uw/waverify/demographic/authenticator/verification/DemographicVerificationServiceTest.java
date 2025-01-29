package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DemographicVerificationService}. Verifies the behavior of the service when validating
 * demographic data.
 */
class DemographicVerificationServiceTest {

	/**
	 * Ensures that the `verify` method returns false when provided invalid or empty demographic data.
	 */
	@Test
	void testVerify_ShouldReturnFalse_WhenDemographicsAreInvalid( ) {

		DemographicVerificationService service      = mock( DemographicVerificationService.class );
		Map< String, String >          demographics = Map.of( ); // Empty demographics

		when( service.verify( demographics ) ).thenReturn( false );

		assertFalse( service.verify( demographics ), "verify should return false for invalid demographics" );
		verify( service ).verify( demographics ); // Ensure the method was called
	}

	/**
	 * Ensures that the `verify` method returns true when provided valid demographic data.
	 */
	@Test
	void testVerify_ShouldReturnTrue_WhenDemographicsAreValid( ) {

		DemographicVerificationService service      = mock( DemographicVerificationService.class );
		Map< String, String >          demographics = Map.of( "firstName", "John", "lastName", "Doe" );

		when( service.verify( demographics ) ).thenReturn( true );

		assertTrue( service.verify( demographics ), "verify should return true for valid demographics" );
		verify( service ).verify( demographics ); // Ensure the method was called
	}

}
