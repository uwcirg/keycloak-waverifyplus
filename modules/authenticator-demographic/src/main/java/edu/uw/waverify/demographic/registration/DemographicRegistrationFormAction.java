package edu.uw.waverify.demographic.registration;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.authentication.*;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.*;
import org.keycloak.models.utils.FormMessage;

/**
 * Form action for collecting and processing demographic information during user registration.
 * <p>
 * This action ensures that the required demographic fields—first name, last name, and date of birth—are provided during
 * registration and stores them as user attributes.
 * </p>
 */
public
class DemographicRegistrationFormAction implements FormAction {

	/**
	 * Adds demographic attributes to the registration form.
	 *
	 * @param formContext
	 * 		the context of the form authentication process.
	 * @param loginFormsProvider
	 * 		the provider responsible for rendering the form.
	 */
	@Override
	public
	void buildPage( FormContext formContext, LoginFormsProvider loginFormsProvider ) {

		loginFormsProvider.setAttribute( "demographicRequired", true );
	}

	/**
	 * Validates the demographic information submitted by the user.
	 *
	 * @param context
	 * 		the validation context containing form data.
	 */
	@Override
	public
	void validate( ValidationContext context ) {

		var formData = context.getHttpRequest( )
		                      .getDecodedFormParameters( );
		var email       = formData.getFirst( "email" );
		var firstName   = formData.getFirst( "firstName" );
		var lastName    = formData.getFirst( "lastName" );
		var dob         = formData.getFirst( "dob" );
		var authSession = context.getAuthenticationSession( );

		authSession.removeAuthNote( "AUTH_SESSION_ID" );

		if ( firstName == null || firstName.isEmpty( ) || lastName == null || lastName.isEmpty( ) || dob == null || dob.isEmpty( ) || email == null || email.isEmpty( ) ) {
			List< FormMessage > errors = new ArrayList<>( );
			errors.add( new FormMessage( null, "Please provide all required demographic information." ) );
			context.validationError( formData, errors );
			return;
		}

		context.getAuthenticationSession( )
		       .setAuthNote( "email", email );
		context.getAuthenticationSession( )
		       .setAuthNote( "firstName", firstName );
		context.getAuthenticationSession( )
		       .setAuthNote( "lastName", lastName );
		context.getAuthenticationSession( )
		       .setAuthNote( "dob", dob );

		context.success( );
	}

	/**
	 * Stores validated demographic information as user attributes.
	 *
	 * @param context
	 * 		the form context containing the user and session data.
	 */
	@Override
	public
	void success( FormContext context ) {

		var session     = context.getSession( );
		var realm       = context.getRealm( );
		var authSession = context.getAuthenticationSession( );

		var username = authSession.getAuthNote( "username" );
		var email    = authSession.getAuthNote( "email" );

		if ( ( username == null || username.isEmpty( ) ) && ( email == null || email.isEmpty( ) ) ) {
			throw new RuntimeException( "Either username or email is required for registration." );
		}

		var user = email != null && realm.isLoginWithEmailAllowed( ) ? session.users( )
		                                                                      .getUserByEmail( realm, email ) : session.users( )
		                                                                                                               .getUserByUsername( realm, username );

		if ( user == null ) {
			user = session.users( )
			              .addUser( realm, username != null ? username : email );
			user.setEnabled( true );
			if ( email != null ) {
				user.setEmail( email );
				user.setEmailVerified( false );
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

	/**
	 * Indicates whether this action requires an existing user.
	 *
	 * @return {@code false} since this action applies to new user registration.
	 */
	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	/**
	 * Checks if this action is configured for the current user.
	 *
	 * @return {@code true} as this action is always applicable.
	 */
	@Override
	public
	boolean configuredFor( KeycloakSession session, RealmModel realm, UserModel user ) {

		return true;
	}

	/**
	 * Sets any additional required actions for the user.
	 *
	 * @param session
	 * 		the Keycloak session.
	 * @param realm
	 * 		the current realm.
	 * @param user
	 * 		the current user.
	 */
	@Override
	public
	void setRequiredActions( KeycloakSession session, RealmModel realm, UserModel user ) {
		// No additional required actions.
	}

	/**
	 * Cleans up resources used by this form action.
	 */
	@Override
	public
	void close( ) {
		// No resources to clean up.
	}

}
