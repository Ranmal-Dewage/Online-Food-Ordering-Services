package com.ranmal.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleApiExceptions(NotFoundException notFoundException) {
        HttpStatus resourceNotFound = HttpStatus.NOT_FOUND;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(notFoundException.getErrorMessage(),
                resourceNotFound.getReasonPhrase(), resourceNotFound.value());
        return new ResponseEntity<>(apiExceptionResponse, resourceNotFound);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiExceptionResponse> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(illegalArgumentException.getMessage(),
                badRequest.getReasonPhrase(), badRequest.value());
        return new ResponseEntity<>(apiExceptionResponse, badRequest);
    }

}
