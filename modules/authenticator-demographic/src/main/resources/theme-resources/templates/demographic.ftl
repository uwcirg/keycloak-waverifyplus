<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
    <#if section = "title">
        ${msg("loginTitle",realm.name)}
    <#elseif section = "header">
        ${msg("loginTitleHtml",realm.name)}
    <#elseif section = "info" >
        <#if realm.password && realm.registrationAllowed && !usernameEditDisabled??>
		<div id="kc-registration">
			<span>${msg("noAccount")} <a tabindex="6" href="${url.registrationUrl}">${msg("doRegister")}</a></span>
		</div>
        </#if>
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
			    <div class="${properties.kcLabelWrapperClass!}">
				    <label for="lastName"
				           class="${properties.kcLabelClass!}">${msg("lastName", "Last Name")}</label>
			    </div>
			    <div class="${properties.kcInputWrapperClass!}">
				    <input type="text" id="lastName" name="lastName" class="${properties.kcInputClass!}"
				           required/>
			    </div>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <div class="${properties.kcLabelWrapperClass!}">
				    <label for="dob"
				           class="${properties.kcLabelClass!}">${msg("dateOfBirth", "Phone Number")}</label>
			    </div>
			    <div class="${properties.kcInputWrapperClass!}">
				    <input type="date" id="dob" name="dob" class="${properties.kcInputClass!}"
				           required/>
			    </div>
		    </div>

		    <div class="${properties.kcFormGroupClass!}">
			    <div class="${properties.kcLabelWrapperClass!}">
				    <label for="phoneNumber"
				           class="${properties.kcLabelClass!}">${msg("phoneNumber", "Phone Number")}</label>
			    </div>
			    <div class="${properties.kcInputWrapperClass!}">
				    <input type="tel" id="phoneNumber" name="phoneNumber"
				           class="${properties.kcInputClass!}"
				           required/>
			    </div>
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
