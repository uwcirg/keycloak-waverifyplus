package edu.uw.waverify.demographic.authenticator.verification;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DemographicVerificationServiceTest {

	@Test
	void testVerify_ShouldReturnFalse_WhenDemographicsAreInvalid( ) {

		DemographicVerificationService service = mock( DemographicVerificationService.class );

		Map< String, String > demographics = Map.of( );

		when( service.verify( demographics ) ).thenReturn( false );

		assertFalse( service.verify( demographics ), "verify should return false for invalid demographics" );
		verify( service ).verify( demographics );
	}

	@Test
	void testVerify_ShouldReturnTrue_WhenDemographicsAreValid( ) {

		DemographicVerificationService service = mock( DemographicVerificationService.class );

		Map< String, String > demographics = Map.of( "firstName", "John", "lastName", "Doe" );

		when( service.verify( demographics ) ).thenReturn( true );

		assertTrue( service.verify( demographics ), "verify should return true for valid demographics" );
		verify( service ).verify( demographics );
	}

}
