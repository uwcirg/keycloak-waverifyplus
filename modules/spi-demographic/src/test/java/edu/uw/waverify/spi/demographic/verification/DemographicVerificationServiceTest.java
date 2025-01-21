package edu.uw.waverify.spi.demographic.verification;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DemographicVerificationServiceTest {

	@Test
	void testVerify_ShouldReturnFalse_WhenDemographicsAreInvalid( ) {

		DemographicVerificationService service      = mock( DemographicVerificationService.class );
		String                         userId       = "12345";
		Map< String, String >          demographics = Map.of( );

		when( service.verify( userId, demographics ) ).thenReturn( false );

		assertFalse( service.verify( userId, demographics ), "verify should return false for invalid demographics" );
		verify( service ).verify( userId, demographics );
	}

	@Test
	void testVerify_ShouldReturnTrue_WhenDemographicsAreValid( ) {

		DemographicVerificationService service      = mock( DemographicVerificationService.class );
		String                         userId       = "12345";
		Map< String, String >          demographics = Map.of( "firstName", "John", "lastName", "Doe" );

		when( service.verify( userId, demographics ) ).thenReturn( true );

		assertTrue( service.verify( userId, demographics ), "verify should return true for valid demographics" );
		verify( service ).verify( userId, demographics );
	}

}
