package edu.uw.waverify.demographic.identification;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.keycloak.email.EmailSenderProvider;
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
		String redirectUri = context.getAuthenticationSession( )
		                            .getRedirectUri( );
		var tokenData = UserTokenGenerator.retrieveStoredToken( user );

		if ( tokenData == null ) {
			log.warnf( "No stored authentication token for user %s. Generating a new one.", user.getId( ) );
			tokenData = UserTokenGenerator.generateAndStoreToken( user );
		}

		String encodedRealm    = URLEncoder.encode( realm.getName( ), StandardCharsets.UTF_8 );
		String encodedClientId = URLEncoder.encode( client.getClientId( ), StandardCharsets.UTF_8 );
		String encodedRedirect = URLEncoder.encode( redirectUri, StandardCharsets.UTF_8 );
		String encodedToken    = URLEncoder.encode( tokenData.getHashedToken( ), StandardCharsets.UTF_8 );

		var loginUrl = String.format( LOGIN_URL_TEMPLATE, baseUrl, encodedRealm, encodedClientId, encodedRedirect, encodedToken );

		log.warnf( "Generated login URL: %s", loginUrl );

		var subject = "Your Secure Login Link";
		var body = String.format( """
				                          Hello %s,
				                          
				                          Use the following link to log in securely:
				                          %s
				                          
				                          This link does not expire and can be used multiple times.
				                          
				                          If you did not request this, please ignore this email.
				                          
				                          Regards,
				                          WA-Verify+ Team""", user.getFirstName( ), loginUrl );

		try {
			session.getProvider( EmailSenderProvider.class )
			       .send( realm.getSmtpConfig( ), user, subject, body, body );
			log.infof( "Login email sent successfully to %s", email );
		} catch ( Exception e ) {
			log.errorf( "Failed to send login email to %s: %s", email, e.getMessage( ) );
		}
	}

}
