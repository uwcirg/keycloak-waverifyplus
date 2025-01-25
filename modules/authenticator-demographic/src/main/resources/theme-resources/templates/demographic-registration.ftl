<div class="login">
	<h1>${messages["registerTitle"]}</h1>
	<form id="registration-form" action="${url.registerAction}" method="post">
		<div class="form-group">
			<label for="firstName">${messages["firstName"]}</label>
			<input type="text" id="firstName" name="firstName" value="${user.firstName!}"
			       class="form-control" required/>
		</div>

		<div class="form-group">
			<label for="lastName">${messages["lastName"]}</label>
			<input type="text" id="lastName" name="lastName" value="${user.lastName!}" class="form-control"
			       required/>
		</div>

		<div class="form-group">
			<label for="dob">${messages["dateOfBirth"]}</label>
			<input type="date" id="dob" name="dob" class="form-control" required/>
		</div>

		<div class="form-group">
			<label for="address">${messages["address"]}</label>
			<textarea id="address" name="address" class="form-control" rows="3" required></textarea>
		</div>

		<div class="form-group">
			<label for="phoneNumber">${messages["phoneNumber"]}</label>
			<input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" required/>
		</div>

		<div class="form-group">
			<label for="age">${messages["age"]}</label>
			<input type="number" id="age" name="age" class="form-control" min="0" required/>
		</div>

		<div class="form-group">
			<label for="gender">${messages["gender"]}</label>
			<select id="gender" name="gender" class="form-control" required>
				<option value="">${messages["selectGender"]}</option>
				<option value="male">${messages["male"]}</option>
				<option value="female">${messages["female"]}</option>
				<option value="other">${messages["other"]}</option>
			</select>
		</div>

		<div class="form-group">
			<label for="location">${messages["location"]}</label>
			<input type="text" id="location" name="location" class="form-control" required/>
		</div>

		<div class="form-group">
			<button type="submit" class="btn btn-primary">${messages["doRegister"]}</button>
		</div>
	</form>
</div>
