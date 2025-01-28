package edu.uw.waverify.mvp.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public
class ValidationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnBadRequestForInvalidData( ) throws Exception {

		String requestBody = """
				{
				    "firstName": "",
				    "lastName": "",
				    "dob": "1990-01-01"
				}
				""";

		mockMvc.perform( MockMvcRequestBuilders.post( "/api/validation" )
		                                       .contentType( MediaType.APPLICATION_JSON )
		                                       .content( requestBody ) )
		       .andExpect( status( ).isBadRequest( ) );
	}

	@Test
	void shouldReturnValidResponseForValidData( ) throws Exception {

		String requestBody = """
				{
				    "firstName": "John",
				    "lastName": "Doe",
				    "dob": "1990-01-01"
				}
				""";

		mockMvc.perform( MockMvcRequestBuilders.post( "/api/validation" )
		                                       .contentType( MediaType.APPLICATION_JSON )
		                                       .content( requestBody ) )
		       .andExpect( status( ).isOk( ) )
		       .andExpect( jsonPath( "$.valid" ).value( true ) );
	}

}
