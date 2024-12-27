package com.example.SpringBootREST3.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorResponse> handleOrderException(OrderException oe) {
        ErrorResponse errorResponse = new ErrorResponse(oe.getErrorCode(), oe.getErrorMessage(), oe.getHttpStatus());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(SPEException.class)
    public ResponseEntity<ErrorCodeProperties> handleSPEException(SPEException speException) {
        ErrorCodeProperties errorCodeProperties = new ErrorCodeProperties(speException.getGroup(),
                                                                          speException.getType(),
                                                                          speException.getMessage(),
                                                                          speException.getInfoMessage(),
                                                                          speException.getHttpStatus());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorCodeProperties);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException me) {
        String errorMessage = "Error in object '%s', error in field '%s', error is '%s'";
        FieldError fieldError = me.getBindingResult().getFieldError();
        errorMessage = String.format(errorMessage, fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        ErrorResponse errorResponse = new ErrorResponse("101", errorMessage, HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException de){
        String errorMessage  = de.getMostSpecificCause().getMessage();
        ErrorResponse errorResponse = new ErrorResponse("401", errorMessage, HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

}
