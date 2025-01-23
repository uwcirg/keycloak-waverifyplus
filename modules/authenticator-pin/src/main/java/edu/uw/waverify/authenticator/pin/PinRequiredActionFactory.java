package edu.uw.waverify.authenticator.pin;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public
class PinRequiredActionFactory implements RequiredActionFactory {

	private static final PinRequiredAction SINGLETON = new PinRequiredAction( );

	@Override
	public
	RequiredActionProvider create( KeycloakSession session ) {

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

		return PinRequiredAction.PROVIDER_ID;
	}

	@Override
	public
	String getDisplayText( ) {

		return "PIN";
	}

}
