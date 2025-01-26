package edu.uw.waverify.demographic.registration;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.authentication.*;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.*;
import org.keycloak.models.utils.FormMessage;
import org.keycloak.sessions.AuthenticationSessionModel;

import jakarta.ws.rs.core.MultivaluedMap;

/**
 * Form action to collect and process demographic information during user registration.
 */
public
class DemographicRegistrationFormAction implements FormAction {

	/**
	 * Builds the registration form page by adding demographic fields.
	 *
	 * @param formContext
	 * 		the form context.
	 * @param loginFormsProvider
	 * 		the provider for rendering login forms.
	 */
	@Override
	public
	void buildPage( FormContext formContext, LoginFormsProvider loginFormsProvider ) {

		loginFormsProvider.setAttribute( "demographicRequired", true );
	}


	/**
	 * Validates the demographic data submitted during registration.
	 *
	 * @param context
	 * 		the validation context containing submitted form data.
	 */
	@Override
	public
	void validate( ValidationContext context ) {

		MultivaluedMap< String, String > formData = context.getHttpRequest( )
		                                                   .getDecodedFormParameters( );
		String age      = formData.getFirst( "age" );
		String gender   = formData.getFirst( "gender" );
		String location = formData.getFirst( "location" );

		if ( age == null || age.isEmpty( ) || gender == null || gender.isEmpty( ) || location == null || location.isEmpty( ) ) {
			List< FormMessage > errors = new ArrayList<>( );
			errors.add( new FormMessage( null, "Please provide all demographic information." ) );
			context.validationError( formData, errors );
			return;
		}

		context.getAuthenticationSession( )
		       .setAuthNote( "age", age );
		context.getAuthenticationSession( )
		       .setAuthNote( "gender", gender );
		context.getAuthenticationSession( )
		       .setAuthNote( "location", location );

		context.success( );
	}


	/**
	 * Stores demographic data in the user's attributes upon successful validation.
	 *
	 * @param context
	 * 		the form context containing user and session data.
	 */
	@Override
	public
	void success( FormContext context ) {

		AuthenticationSessionModel session = context.getAuthenticationSession( );
		UserModel                  user    = context.getUser( );

		user.setAttribute( "age", List.of( session.getAuthNote( "age" ) ) );
		user.setAttribute( "gender", List.of( session.getAuthNote( "gender" ) ) );
		user.setAttribute( "location", List.of( session.getAuthNote( "location" ) ) );
	}

	/**
	 * Indicates whether this action requires an existing user.
	 *
	 * @return {@code false} as this action applies to new user registration.
	 */
	@Override
	public
	boolean requiresUser( ) {

		return false;
	}

	/**
	 * Indicates whether this action is configured for the current user.
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
		// No required actions need to be set for this form.
	}

	/**
	 * Closes any resources used by this form action.
	 */
	@Override
	public
	void close( ) {
		// No resources to close.
	}

}
