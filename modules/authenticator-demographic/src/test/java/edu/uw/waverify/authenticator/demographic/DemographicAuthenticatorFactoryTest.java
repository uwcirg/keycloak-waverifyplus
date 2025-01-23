package edu.uw.waverify.authenticator.demographic;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import edu.uw.waverify.authenticator.demographic.verification.DemographicVerificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DemographicAuthenticatorFactoryTest {

	@Mock
	private KeycloakSession mockSession;

	@Mock
	private KeycloakSessionFactory mockSessionFactory;

	private DemographicAuthenticatorFactory factory;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		factory = new DemographicAuthenticatorFactory( );
	}

	@Test
	void testClose_NoOp( ) {

		factory.close( );

		// No specific action expected; just ensure no exceptions occur
	}

	@Test
	void testCreate_ReturnsDemographicProvider( ) {

		DemographicAuthenticatorFactory.setVerificationService( mock( DemographicVerificationService.class ) );

		var provider = factory.create( mockSession );

		assertNotNull( provider, "Provider should not be null" );
		assertInstanceOf( DemographicAuthenticatorImpl.class, provider, "Provider should be an instance of DemographicAuthenticatorImpl" );
	}

	@Test
	void testGetConfigMetadata_ReturnsEmptyList( ) {

		assertTrue( factory.getConfigMetadata( )
		                   .isEmpty( ), "Expected an empty configuration metadata list" );
	}

	@Test
	void testGetId_ReturnsCorrectId( ) {

		assertEquals( DemographicAuthenticatorFactory.PROVIDER_ID, factory.getId( ) );
	}

	@Test
	void testInit_SetsVerificationService( ) {

		factory.init( null );
		var verificationService = DemographicAuthenticatorFactory.getVerificationService( );
		assertInstanceOf( DemographicVerificationService.class, verificationService );
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
