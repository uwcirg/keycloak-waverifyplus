<div class="login">
	<h1>${msg("registerTitle", "Register")}</h1>
	<form id="registration-form" action="${url.registerAction}" method="post">
		<div class="form-group">
			<label for="firstName">${msg("firstName", "First Name")}</label>
			<input type="text" id="firstName" name="firstName" value="${user.firstName!}"
			       class="form-control" required/>
		</div>

		<div class="form-group">
			<label for="lastName">${msg("lastName", "Last Name")}</label>
			<input type="text" id="lastName" name="lastName" value="${user.lastName!}" class="form-control"
			       required/>
		</div>

		<div class="form-group">
			<label for="dob">${msg("dateOfBirth", "Date of Birth")}</label>
			<input type="date" id="dob" name="dob" class="form-control" required/>
		</div>

		<div class="form-group">
			<label for="address">${msg("address", "Address")}</label>
			<textarea id="address" name="address" class="form-control" rows="3" required></textarea>
		</div>

		<div class="form-group">
			<label for="phoneNumber">${msg("phoneNumber", "Phone Number")}</label>
			<input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" required/>
		</div>

		<div class="form-group">
			<label for="age">${msg("age", "Age")}</label>
			<input type="number" id="age" name="age" class="form-control" min="0" required/>
		</div>

		<div class="form-group">
			<label for="gender">${msg("gender", "Gender")}</label>
			<select id="gender" name="gender" class="form-control" required>
				<option value="">${msg("selectGender", "Select Gender")}</option>
				<option value="male">${msg("male", "Male")}</option>
				<option value="female">${msg("female", "Female")}</option>
				<option value="other">${msg("other", "Other")}</option>
			</select>
		</div>

		<div class="form-group">
			<label for="location">${msg("location", "Location")}</label>
			<input type="text" id="location" name="location" class="form-control" required/>
		</div>

		<div class="form-group">
			<button type="submit" class="btn btn-primary">${msg("doRegister", "Register")}</button>
		</div>
	</form>
</div>
