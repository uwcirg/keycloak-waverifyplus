<#macro demographicRegistration>
    <@field.input name="firstName" label=msg("firstName", "First Name") autofocus=true autocomplete="firstName" value='' required=true />
    <@field.input name="lastName" label=msg("lastName", "Last Name") autofocus=true autocomplete="lastName" value='' required=true/>
    <@field.input name="dateOfBirth" label=msg("dateOfBirth", "Date of Birth") autofocus=true autocomplete="dateOfBirth" value='' type="date" required=true/>
	<p>${msg("createPin")}</p>
    <@demographicFiled.setPin name="pin" label=msg("pin", "PIN") autofocus=true autocomplete="pin" value='' required=true />
	<div class="form-note">
		<span>Note:</span>
		<p>${msg("notePin")}</p>
	</div>
</#macro>
