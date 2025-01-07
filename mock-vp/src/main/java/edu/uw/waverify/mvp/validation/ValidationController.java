package edu.uw.waverify.mvp.validation;

import java.time.LocalDate;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
public
class ValidationController {

	private
	boolean isDataValid( ValidationRequest request ) {
		// Basic validation logic (can be extended for specific rules)
		return !request.getFirstName( )
		               .isBlank( ) && !request.getLastName( )
		                                      .isBlank( ) && request.getDob( )
		                                                            .isBefore( LocalDate.now( ) );
	}

	@PostMapping
	public
	ResponseEntity< ValidationResponse > validate( @Valid @RequestBody ValidationRequest request ) {

		var isValid  = isDataValid( request );
		var response = new ValidationResponse( isValid );
		return ResponseEntity.status( HttpStatus.OK )
		                     .body( response );
	}

}
