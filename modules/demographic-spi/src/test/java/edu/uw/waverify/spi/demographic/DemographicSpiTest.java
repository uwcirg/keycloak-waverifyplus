package edu.uw.waverify.spi.demographic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemographicSpiTest {

	private final DemographicSpi demographicSpi = new DemographicSpi( );

	@Test
	void testGetName_ReturnsCorrectName( ) {

		assertEquals( "demographic-validation", demographicSpi.getName( ), "getName should return 'demographic-validation'" );
	}

	@Test
	void testGetProviderClass_ReturnsCorrectClass( ) {

		assertEquals( DemographicProvider.class, demographicSpi.getProviderClass( ), "getProviderClass should return DemographicProvider.class" );
	}

	@Test
	void testGetProviderFactoryClass_ReturnsCorrectClass( ) {

		assertEquals( DemographicProviderFactory.class, demographicSpi.getProviderFactoryClass( ), "getProviderFactoryClass should return DemographicProviderFactory.class" );
	}

	@Test
	void testIsEnabled_ReturnsTrue( ) {

		assertTrue( demographicSpi.isEnabled( ), "isEnabled should return true" );
	}

	@Test
	void testIsInternal_ReturnsFalse( ) {

		assertFalse( demographicSpi.isInternal( ), "isInternal should return false" );
	}

}
