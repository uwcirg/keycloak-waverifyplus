package edu.uw.waverify.pin;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.Config;
import org.keycloak.authentication.*;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

public
class PinAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

	private static final PinAuthenticator               SINGLETON           = new PinAuthenticator( );
	private static final List< ProviderConfigProperty > configProperties    = new ArrayList<>( );
	private static final Requirement[]                  REQUIREMENT_CHOICES = { REQUIRED, ALTERNATIVE, DISABLED };
	public static final  String                         PROVIDER_ID         = "pin-authenticator";

	static {
		ProviderConfigProperty property;
		property = new ProviderConfigProperty( );
		property.setName( "cookie.max.age" );
		property.setLabel( "Cookie Max Age" );
		property.setType( ProviderConfigProperty.STRING_TYPE );
		property.setHelpText( "Max age in seconds of the PIN_COOKIE." );
		configProperties.add( property );
	}

	@Override
	public
	Authenticator create( KeycloakSession session ) {

		return SINGLETON;
	}

	@Override
	public
	void init( Config.Scope config ) {

	}

	@Override
	public
	void postInit( KeycloakSessionFactory factory ) {

	}

	@Override
	public
	void close( ) {

	}

	@Override
	public
	String getId( ) {

		return PROVIDER_ID;
	}

	@Override
	public
	String getDisplayType( ) {

		return "PIN";
	}

	@Override
	public
	String getReferenceCategory( ) {

		return "PIN";
	}

	@Override
	public
	boolean isConfigurable( ) {

		return true;
	}

	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return REQUIREMENT_CHOICES;
	}

	@Override
	public
	boolean isUserSetupAllowed( ) {

		return true;
	}

	@Override
	public
	String getHelpText( ) {

		return "User's PIN.";
	}

	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return configProperties;
	}

}
