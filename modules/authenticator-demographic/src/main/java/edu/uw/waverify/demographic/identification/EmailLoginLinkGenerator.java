package edu.uw.waverify.demographic.identification;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.models.*;

import edu.uw.waverify.token.UserTokenGenerator;

import lombok.extern.jbosslog.JBossLog;

/**
 * Utility class for generating and sending a login email containing a unique authentication link.
 */
@JBossLog
public
class EmailLoginLinkGenerator {

	private static final String LOGIN_URL_TEMPLATE = "%srealms/%s/protocol/openid-connect/auth?" + "response_type=code&" + "client_id=%s&" + "redirect_uri=%s&" + "user_token=%s";

	/**
	 * Sends a login link email to the specified user.
	 *
	 * @param session
	 * 		The Keycloak session.
	 * @param user
	 * 		The user to whom the email is sent.
	 */
	public static
	void sendLoginEmail( KeycloakSession session, UserModel user ) {

		var email = user.getEmail( );
		if ( email == null || email.isBlank( ) ) {
			log.warnf( "Skipping email for user %s: No email set", user.getId( ) );
			return;
		}

		var context = session.getContext( );
		var realm   = context.getRealm( );
		var baseUrl = context.getUri( )
		                     .getBaseUri( )
		                     .toString( );

		ClientModel client = context.getAuthenticationSession( )
		                            .getClient( );
		var redirectUri = context.getAuthenticationSession( )
		                         .getRedirectUri( );
		var tokenData = UserTokenGenerator.retrieveStoredToken( user );

		if ( tokenData == null ) {
			tokenData = UserTokenGenerator.generateAndStoreToken( user );
		}

		var encodedRealm    = URLEncoder.encode( realm.getName( ), StandardCharsets.UTF_8 );
		var encodedClientId = URLEncoder.encode( client.getClientId( ), StandardCharsets.UTF_8 );
		var encodedRedirect = URLEncoder.encode( redirectUri, StandardCharsets.UTF_8 );
		var encodedToken    = URLEncoder.encode( tokenData.getHashedToken( ), StandardCharsets.UTF_8 );

		var loginUrl = String.format( LOGIN_URL_TEMPLATE, baseUrl, encodedRealm, encodedClientId, encodedRedirect, encodedToken );

		var variables = new HashMap< String, Object >( );
		variables.put( "user", user );
		variables.put( "link", loginUrl );

		var subject = "International Patient Summary Prototype";

		try {
			var provider = session.getProvider( EmailTemplateProvider.class );

			provider.setRealm( realm )
			        .setUser( user )
			        .send( subject, "access-link", variables );

			log.infof( "Login email sent successfully to %s", email );
		} catch ( Exception e ) {
			log.errorf( "Failed to send login email to %s: %s", email, e.getMessage( ) );
		}
	}

}
