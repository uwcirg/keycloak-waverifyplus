package edu.uw.waverify.demographic.authenticator.verification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemographicDataTest {

	private DemographicData demographicData;

	@BeforeEach
	void setUp( ) {

		demographicData = new DemographicData( );
	}

	@Test
	void testDefaultConstructor( ) {

		assertNull( demographicData.getFirstName( ) );
		assertNull( demographicData.getLastName( ) );
		assertNull( demographicData.getDateOfBirth( ) );
		assertNull( demographicData.getEmail( ) );
		assertNull( demographicData.getPhoneNumber( ) );
	}

	@Test
	void testEqualsAndHashCode( ) {

		DemographicData data1 = new DemographicData( "Alice", "Brown", "2000-08-10", "alice.brown@example.com", "1112223333" );
		DemographicData data2 = new DemographicData( "Alice", "Brown", "2000-08-10", "alice.brown@example.com", "1112223333" );

		assertEquals( data1, data2 );
		assertEquals( data1.hashCode( ), data2.hashCode( ) );
	}

	@Test
	void testParameterizedConstructor( ) {

		demographicData = new DemographicData( "John", "Doe", "1990-01-01", "john.doe@example.com", "1234567890" );

		assertEquals( "John", demographicData.getFirstName( ) );
		assertEquals( "Doe", demographicData.getLastName( ) );
		assertEquals( "1990-01-01", demographicData.getDateOfBirth( ) );
		assertEquals( "john.doe@example.com", demographicData.getEmail( ) );
		assertEquals( "1234567890", demographicData.getPhoneNumber( ) );
	}

	@Test
	void testSettersAndGetters( ) {

		demographicData.setFirstName( "Jane" );
		demographicData.setLastName( "Smith" );
		demographicData.setDateOfBirth( "1985-05-15" );
		demographicData.setEmail( "jane.smith@example.com" );
		demographicData.setPhoneNumber( "9876543210" );

		assertEquals( "Jane", demographicData.getFirstName( ) );
		assertEquals( "Smith", demographicData.getLastName( ) );
		assertEquals( "1985-05-15", demographicData.getDateOfBirth( ) );
		assertEquals( "jane.smith@example.com", demographicData.getEmail( ) );
		assertEquals( "9876543210", demographicData.getPhoneNumber( ) );
	}

	@Test
	void testToString( ) {

		demographicData = new DemographicData( "Chris", "Evans", "1970-06-13", "chris.evans@example.com", "5554443333" );
		String expected = "DemographicData{firstName='Chris', lastName='Evans', dateOfBirth='1970-06-13', email='chris.evans@example.com', phoneNumber='5554443333'}";

		assertEquals( expected, demographicData.toString( ) );
	}

}
