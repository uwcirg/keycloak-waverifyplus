package edu.uw.waverify.spi.demographic.verification;

import java.util.Map;

public
interface DemographicVerificationService {

	boolean verify( String userId, Map< String, String > demographics );

}
