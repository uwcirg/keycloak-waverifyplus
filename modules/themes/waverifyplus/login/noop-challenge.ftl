<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>
    <#if section = "header">
	    <i class="fa-solid fa-virus-covid"></i>
        ${msg("loginAccountTitle")}
    <#elseif section = "form">
	    <div id="kc-form" class="container-sm">
		    <div id="kc-form-wrapper">
			    <p> Thank you. </p>
		    </div>
	    </div>
    </#if>
</@layout.registrationLayout>
