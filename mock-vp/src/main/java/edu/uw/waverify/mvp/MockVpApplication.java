package edu.uw.waverify.mvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public
class MockVpApplication {

	public static
	void main( String[] args ) {

		SpringApplication.run( MockVpApplication.class, args );
	}

	@Bean
	public
	ObjectMapper objectMapper( ) {

		ObjectMapper objectMapper = new ObjectMapper( );
		objectMapper.registerModule( new JavaTimeModule( ) );
		return objectMapper;
	}

}
