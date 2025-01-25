<#import "select.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "title">
        ${msg("loginTitle",realm.name)}
    <#elseif section = "header">
        ${msg("loginTitleHtml",realm.name)}
    <#elseif section == "form">
	    <form id="registration-form" action="${url.loginAction!'#'}" method="post"
	          class="${properties.kcFormClass!}">
		    <div class="${properties.kcFormGroupClass!}">
			    <div class="${properties.kcLabelWrapperClass!}">
				    <label for="firstName"
				           class="${properties.kcLabelClass!}">${msg("firstName", "First Name")}</label>
			    </div>
			    <div class="${properties.kcInputWrapperClass!}">
				    <input type="text" id="firstName" name="firstName"
				           class="${properties.kcInputClass!}"
				           required/>
			    </div>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <label for="lastName"
			           class="${properties.kcLabelClass!}">${msg("lastName", "Last Name")}</label>
			    <input type="text" id="lastName" name="lastName" class="${properties.kcInputClass!}"
			           required/>
		    </div>


		    <div class="${properties.kcFormGroupClass!}">
			    <label for="dob"
			           class="${properties.kcLabelClass!}">${msg("dateOfBirth", "Date of Birth")}</label>
			    <input type="date" id="dob" name="dob" class="${properties.kcInputClass!}" required/>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <label for="address"
			           class="${properties.kcLabelClass!}">${msg("address", "Address")}</label>
			    <textarea id="address" name="address" class="${properties.kcInputClass!}" rows="3"
			              required></textarea>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <label for="phoneNumber"
			           class="${properties.kcLabelClass!}">${msg("phoneNumber", "Phone Number")}</label>
			    <input type="tel" id="phoneNumber" name="phoneNumber" class="${properties.kcInputClass!}"
			           required/>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <label for="age" class="${properties.kcLabelClass!}">${msg("age", "Age")}</label>
			    <input type="number" id="age" name="age" class="${properties.kcInputClass!}" min="0"
			           required/>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <label for="gender" class="${properties.kcLabelClass!}">${msg("gender", "Gender")}</label>
			    <select id="gender" name="gender" class="${properties.kcInputClass!}" required>
				    <option value="">${msg("selectGender", "Select Gender")}</option>
				    <option value="male">${msg("male", "Male")}</option>
				    <option value="female">${msg("female", "Female")}</option>
				    <option value="other">${msg("other", "Other")}</option>
			    </select>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <label for="location"
			           class="${properties.kcLabelClass!}">${msg("location", "Location")}</label>
			    <input type="text" id="location" name="location" class="${properties.kcInputClass!}"
			           required/>
		    </div>

		    <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
			    <div class="${properties.kcFormButtonsWrapperClass!}">
				    <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}"
				           name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
			    </div>
		    </div>
	    </form>
    </#if>
</@layout.registrationLayout>
