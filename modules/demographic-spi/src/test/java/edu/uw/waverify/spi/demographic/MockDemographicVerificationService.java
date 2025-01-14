package edu.uw.waverify.spi.demographic;

import java.util.Map;

import edu.uw.waverify.spi.demographic.verification.DemographicVerificationService;

public
class MockDemographicVerificationService implements DemographicVerificationService {

	@Override
	public
	boolean verify( String userId, Map< String, String > demographics ) {

		return demographics.containsKey( "firstName" ) && demographics.containsKey( "lastName" );
	}

}
