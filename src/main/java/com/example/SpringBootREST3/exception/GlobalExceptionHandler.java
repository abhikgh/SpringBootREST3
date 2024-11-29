package com.example.SpringBootREST3.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorResponse> handleOrderException(OrderException oe){
        ErrorResponse errorResponse = new ErrorResponse(oe.getErrorCode(), oe.getErrorMessage(), oe.getHttpStatus());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessage = "Error in object '%s', error in field '%s', error is '%s'";
        FieldError fieldError = exception.getBindingResult().getFieldError();
        errorMessage = String.format(errorMessage, fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        ErrorResponse errorResponse = new ErrorResponse("101", errorMessage, HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
}
