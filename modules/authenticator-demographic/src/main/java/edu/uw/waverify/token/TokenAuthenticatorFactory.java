package edu.uw.waverify.token;

import java.util.List;

import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import static org.keycloak.Config.Scope;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.*;

public
class TokenAuthenticatorFactory implements AuthenticatorFactory {

	public static final String PROVIDER_ID = "token-authenticator";

	@Override
	public
	Authenticator create( KeycloakSession session ) {

		return new TokenAuthenticator( );
	}

	@Override
	public
	void init( Scope scope ) {

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

		return "Token Authenticator";
	}

	@Override
	public
	String getReferenceCategory( ) {

		return "token";
	}

	@Override
	public
	boolean isConfigurable( ) {

		return false;
	}

	@Override
	public
	Requirement[] getRequirementChoices( ) {

		return new Requirement[] { REQUIRED, DISABLED };
	}

	@Override
	public
	boolean isUserSetupAllowed( ) {

		return false;
	}

	@Override
	public
	String getHelpText( ) {

		return "Token Authenticator";
	}

	@Override
	public
	List< ProviderConfigProperty > getConfigProperties( ) {

		return List.of( );
	}

}
