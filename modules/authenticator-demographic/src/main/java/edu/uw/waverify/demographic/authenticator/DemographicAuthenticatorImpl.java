package edu.uw.waverify.demographic.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.models.*;

import edu.uw.waverify.demographic.authenticator.verification.*;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.jbosslog.JBossLog;

/**
 * Implementation of the {@link DemographicAuthenticator} interface.
 * <p>
 * This class handles demographic authentication within a Keycloak authentication flow. It collects user demographic
 * data, validates it using a verification service, and determines whether authentication should proceed.
 * </p>
 */
@Setter
@Getter
@JBossLog
public
class DemographicAuthenticatorImpl implements DemographicAuthenticator {

	private final DemographicVerificationService verificationService;

	/**
	 * Constructs a {@code DemographicAuthenticatorImpl} instance with a Keycloak session and a demographic verification
	 * service.
	 *
	 * @param session
	 * 		the Keycloak session used for authentication flow interactions.
	 * @param baseUrl
	 * 		the base URL for the demographic verification service.
	 */
	public
	DemographicAuthenticatorImpl( KeycloakSession session, String baseUrl ) {

		this.verificationService = new DemographicVerificationServiceImpl( session, baseUrl );
	}

	/**
	 * Initiates the authentication process by displaying a demographic information form.
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
	@Override
	public
	void authenticate( AuthenticationFlowContext context ) {

		context.form( )
		       .setAttribute( "demographicRequired", true );
		var challenge = context.form( )
		                       .createForm( "login.ftl" );
		context.challenge( challenge );
	}

	/**
	 * Handles the user's submitted demographic information and performs validation.
	 *
	 * @param context
	 * 		the authentication flow context.
	 */
	@Override
	public
	void action( AuthenticationFlowContext context ) {

		var demographicData = DemographicDataHelper.extractFromRequest( context.getHttpRequest( ) );

		if ( !DemographicDataHelper.isValid( demographicData ) ) {
			log.error( "DemographicData is not valid" );
			log.error( demographicData );

			var challenge = context.form( )
			                       .setError( "Demographic validation failed. Please check your details." )
			                       .createForm( "login.ftl" );
			context.failureChallenge( AuthenticationFlowError.INVALID_CREDENTIALS, challenge );
			return;
		}
		save( context, demographicData );
		context.success( );
	}

	/**
	 * Indicates whether a user is required for this authentication process.
	 *
	 * @return {@code false} since demographic validation does not require an authenticated user.
	 */
	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	/**
	 * Determines whether this authenticator is configured for a given user in a realm.
	 *
	 * @param keycloakSession
	 * 		the current Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 *
	 * @return {@code false} since this authenticator does not require specific user configuration.
	 */
	@Override
	public
	boolean configuredFor( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {

		return false;
	}

	/**
	 * Sets required actions for a user, if applicable.
	 *
	 * @param keycloakSession
	 * 		the current Keycloak session.
	 * @param realmModel
	 * 		the Keycloak realm.
	 * @param userModel
	 * 		the user model.
	 */
	@Override
	public
	void setRequiredActions( KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel ) {
		// No required actions for this authenticator
	}

	/**
	 * Cleans up resources by nullifying the verification service reference.
	 */
	@Override
	public
	void close( ) {
		// No specific cleanup required.
	}

	public
	void save( AuthenticationFlowContext context, DemographicData demographicData ) {

		var session     = context.getSession( );
		var realm       = context.getRealm( );
		var authSession = context.getAuthenticationSession( );

		var email = demographicData.getEmail( );
		if ( ( email == null || email.isEmpty( ) ) ) {
			throw new RuntimeException( "Either username or email is required for registration." );
		}

		var user = session.users( )
		                  .getUserByEmail( realm, email );

		if ( user == null ) {
			user = session.users( )
			              .addUser( realm, email );
			user.setEnabled( true );
			if ( email != null ) {
				user.setEmail( email );
				user.setEmailVerified( true );
			}
		}

		var firstName = authSession.getAuthNote( "firstName" );
		var lastName  = authSession.getAuthNote( "lastName" );
		var dob       = authSession.getAuthNote( "dob" );

		user.setEmail( email );
		user.setFirstName( firstName );
		user.setLastName( lastName );
		user.setSingleAttribute( "dob", dob );

		context.setUser( user );
	}

}
