package edu.uw.waverify.demographic.authenticator;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import edu.uw.waverify.demographic.authenticator.verification.DemographicVerificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DemographicAuthenticatorFactoryTest {

	@Mock
	private KeycloakSession mockSession;

	@Mock
	private KeycloakSessionFactory mockSessionFactory;

	@Mock
	private Config.Scope configScope;

	private DemographicAuthenticatorFactory factory;

	@BeforeEach
	void setUp( ) {

		MockitoAnnotations.openMocks( this );
		factory = new DemographicAuthenticatorFactory( );
		factory.init( configScope );

		var baseUrl = "http://localhost:8080/api/validation";
		when( configScope.get( "baseUrl" ) ).thenReturn( baseUrl );

		// Setup session factory mock
		when( mockSessionFactory.create( ) ).thenReturn( mockSession );
	}

	@Test
	void testClose_NoOp( ) {

		assertDoesNotThrow( ( ) -> factory.close( ) );
	}

	@Test
	void testCreate_ReturnsDemographicProvider( ) {

		var provider = factory.create( mockSession );

		assertNotNull( provider, "Provider should not be null" );
		assertInstanceOf( DemographicAuthenticatorImpl.class, provider, "Provider should be an instance of DemographicAuthenticatorImpl" );
	}

	@Test
	void testGetConfigMetadata_ReturnsEmptyList( ) {

		assertTrue( factory.getConfigMetadata( )
		                   .isEmpty( ), "Configuration metadata should be empty" );
	}

	@Test
	void testGetId_ReturnsCorrectId( ) {

		assertEquals( DemographicAuthenticatorFactory.PROVIDER_ID, factory.getId( ), "Factory ID should match expected constant" );
	}

	@Test
	void testInit_SetsVerificationService( ) {

		var provider = factory.create( mockSession );
		var service  = provider.getVerificationService( );

		assertNotNull( service, "Verification service must be initialized" );
		assertInstanceOf( DemographicVerificationService.class, service, "Service should be correct implementation type" );
	}

	@Test
	void testOrder_ReturnsZero( ) {

		assertEquals( 0, factory.order( ), "Default order should be 0" );
	}

	@Test
	void testPostInit_NoOp( ) {

		assertDoesNotThrow( ( ) -> factory.postInit( mockSessionFactory ) );
	}

}
