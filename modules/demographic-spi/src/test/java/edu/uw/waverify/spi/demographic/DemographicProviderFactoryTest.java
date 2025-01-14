package edu.uw.waverify.spi.demographic;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import edu.uw.waverify.spi.demographic.verification.DemographicVerificationService;
import edu.uw.waverify.spi.demographic.verification.DemographicVerificationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DemographicProviderFactoryTest {

	@Mock
	private KeycloakSession mockSession;

	@Mock
	private KeycloakSessionFactory mockSessionFactory;

	private DemographicProviderFactory factory;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		factory = new DemographicProviderFactory( );
	}

	@Test
	void testClose_NoOp( ) {

		factory.close( );

		// No specific action expected; just ensure no exceptions occur
	}

	@Test
	void testCreate_ReturnsDemographicProvider( ) {

		factory.setDemographicVerificationService( mock( DemographicVerificationService.class ) );

		DemographicProvider provider = factory.create( mockSession );

		assertNotNull( provider, "Provider should not be null" );
		assertInstanceOf( DemographicProviderImpl.class, provider, "Provider should be an instance of DemographicProviderImpl" );
	}

	@Test
	void testGetConfigMetadata_ReturnsEmptyList( ) {

		assertTrue( factory.getConfigMetadata( )
		                   .isEmpty( ), "Expected an empty configuration metadata list" );
	}

	@Test
	void testGetId_ReturnsCorrectId( ) {

		assertEquals( DemographicProviderFactory.PROVIDER_ID, factory.getId( ) );
	}

	@Test
	void testInit_SetsVerificationService( ) {

		factory.init( null );

		// Verify that the verification service is initialized as an instance of DemographicVerificationServiceImpl
		var verificationServiceField = factory.getClass( )
		                                      .getDeclaredFields( )[ 1 ];
		verificationServiceField.setAccessible( true );
		try {
			var verificationService = verificationServiceField.get( factory );
			assertInstanceOf( DemographicVerificationServiceImpl.class, verificationService );
		} catch ( IllegalAccessException e ) {
			fail( "Field access error: " + e.getMessage( ) );
		}
	}

	@Test
	void testOrder_ReturnsZero( ) {

		assertEquals( 0, factory.order( ), "Order should return 0 as default priority" );
	}

	@Test
	void testPostInit_NoOp( ) {

		factory.postInit( mockSessionFactory );

		// No specific action expected; just ensure no exceptions occur
	}

}
