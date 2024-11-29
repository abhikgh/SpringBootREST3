package com.example.SpringBootREST3.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

	@Schema(example = "101")
	private String errorCode;
	@Schema(example = "Error in object 'person', error in field 'firstName', error is 'First name must have at-least 5 chars'")
	private String errorMessage;
	@Schema(example = "BAD_REQUEST")
	private HttpStatus httpStatus;

}
