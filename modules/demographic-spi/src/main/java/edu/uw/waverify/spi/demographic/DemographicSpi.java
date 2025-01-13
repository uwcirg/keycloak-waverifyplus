package edu.uw.waverify.spi.demographic;

import org.keycloak.provider.*;

/**
 * SPI definition for Demographic Validation in Keycloak.
 */
public
class DemographicSpi implements Spi {

	/**
	 * Determines whether this SPI is used internally by Keycloak.
	 *
	 * @return {@code false} because this SPI is not limited to internal use.
	 */
	@Override
	public
	boolean isInternal( ) {

		return false;
	}

	/**
	 * Provides the unique name of the SPI.
	 *
	 * @return The name of the SPI, "demographic-validation".
	 */
	@Override
	public
	String getName( ) {

		return "demographic-validation";
	}

	/**
	 * Specifies the provider class for this SPI.
	 *
	 * @return The {@code DemographicProvider} class.
	 */
	@Override
	public
	Class< ? extends Provider > getProviderClass( ) {

		return DemographicProvider.class;
	}

	/**
	 * Specifies the provider factory class for this SPI.
	 *
	 * @return The {@code DemographicProviderFactory} class.
	 */
	@Override
	public
	Class< ? extends ProviderFactory > getProviderFactoryClass( ) {

		return DemographicProviderFactory.class;
	}

	/**
	 * Indicates whether this SPI is enabled.
	 *
	 * @return {@code true}, enabling the SPI by default.
	 */
	@Override
	public
	boolean isEnabled( ) {

		return true;
	}

}
