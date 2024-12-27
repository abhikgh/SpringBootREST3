package com.example.SpringBootREST3.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class SPEException extends RuntimeException{

    private ErrorProperties.Group group;
    private ErrorProperties.Type type;
    private String message;
    private String infoMessage;
    private HttpStatus httpStatus;
}
