package edu.uw.waverify.noop.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.*;

import lombok.extern.jbosslog.JBossLog;

/**
 * A no-operation authenticator that prevents authentication from proceeding.
 * <p>
 * This authenticator presents a challenge page that cannot be fulfilled, effectively blocking the authentication flow.
 * It does not call {@code context.success()}, ensuring that authentication does not progress.
 * </p>
 */
@JBossLog
public
class NoopAuthenticator implements Authenticator {

	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		var challenge = context.form( )
		                       .createForm( "noop-challenge.ftl" );
		context.challenge( challenge );
	}

	@Override
	public
	void action( AuthenticationFlowContext context ) {

		var challenge = context.form( )
		                       .createForm( "noop-challenge.ftl" );
		context.challenge( challenge );
	}

	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return true;
	}

	@Override
	public
	void setRequiredActions( KeycloakSession session, RealmModel realm, UserModel user ) {
		// No required actions.
	}

	@Override
	public
	void close( ) {
		// No cleanup required.
	}

}
