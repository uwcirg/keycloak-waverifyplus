<#macro demographicRegistration>
	<form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.registrationAction}" method="post">
		<div class="${properties.kcFormGroupClass!}">
			<div class="${properties.kcLabelWrapperClass!}">
				<label for="firstName" class="${properties.kcLabelClass!}">
                                    ${msg("firstName", "First Name")}
				</label>
			</div>
			<div class="${properties.kcInputWrapperClass!}">
				<input type="text" id="firstName" name="firstName" class="${properties.kcInputClass!}"
				       required/>
			</div>
		</div>

		<div class="${properties.kcFormGroupClass!}">
			<div class="${properties.kcLabelWrapperClass!}">
				<label for="lastName" class="${properties.kcLabelClass!}">
                                    ${msg("lastName", "Last Name")}
				</label>
			</div>
			<div class="${properties.kcInputWrapperClass!}">
				<input type="text" id="lastName" name="lastName" class="${properties.kcInputClass!}"
				       required/>
			</div>
		</div>

		<div class="${properties.kcFormGroupClass!}">
			<div class="${properties.kcLabelWrapperClass!}">
				<label for="dob" class="${properties.kcLabelClass!}">
                                    ${msg("dateOfBirth", "Date of Birth")}
				</label>
			</div>
			<div class="${properties.kcInputWrapperClass!}">
				<input type="date" id="dob" name="dob" class="${properties.kcInputClass!}" required/>
			</div>
		</div>

		<div class="${properties.kcFormGroupClass!}">
			<div class="${properties.kcLabelWrapperClass!}">
				<label for="phoneNumber" class="${properties.kcLabelClass!}">
                                    ${msg("phoneNumber", "Phone Number")}
				</label>
			</div>
			<div class="${properties.kcInputWrapperClass!}">
				<input type="tel" id="phoneNumber" name="phoneNumber"
				       class="${properties.kcInputClass!}" required/>
			</div>
		</div>
	</form>
</#macro>
