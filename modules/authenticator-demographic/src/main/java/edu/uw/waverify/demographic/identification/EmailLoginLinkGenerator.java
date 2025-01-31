package edu.uw.waverify.demographic.identification;

import org.keycloak.email.EmailSenderProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;

import lombok.extern.jbosslog.JBossLog;

/**
 * Utility class for generating and sending a login email containing a unique authentication link.
 */
@JBossLog
public
class EmailLoginLinkGenerator {

	private static final String LOGIN_URL_TEMPLATE = "%s/realms/%s/login-actions/authenticate?user_token=%s";

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
			log.warnf( "Skipping email login link for user %s as no email is set.", user.getId( ) );
			return;
		}

		var realm = session.getContext( )
		                   .getRealm( );
		var baseUrl = session.getContext( )
		                     .getUri( )
		                     .getBaseUri( )
		                     .toString( );

		var tokenData = UserTokenGenerator.retrieveStoredToken( user );
		if ( tokenData == null ) {
			log.warnf( "No stored authentication token for user %s. Generating a new one.", user.getId( ) );
			tokenData = UserTokenGenerator.generateAndStoreToken( user );
		}

		var loginUrl = String.format( LOGIN_URL_TEMPLATE, baseUrl, realm.getName( ), tokenData.getHashedToken( ) );
		var subject  = "Your Secure Login Link";
		var body     = String.format( "Hello %s,\n\n" + "Use the following link to log in securely:\n%s\n\n" + "This link does not expire and can be used multiple times.\n\n" + "If you did not request this, please ignore this email.\n\n" + "Regards,\nYour Security Team", user.getFirstName( ), loginUrl );

		try {
			session.getProvider( EmailSenderProvider.class )
			       .send( realm.getSmtpConfig( ), user, subject, body, body );
			log.infof( "Login email sent successfully to %s", email );
		} catch ( Exception e ) {
			log.errorf( "Failed to send login email to %s: %s", email, e.getMessage( ) );
		}
	}

}
