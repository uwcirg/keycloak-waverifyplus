<#macro demographicRegistration>
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
</#macro>
