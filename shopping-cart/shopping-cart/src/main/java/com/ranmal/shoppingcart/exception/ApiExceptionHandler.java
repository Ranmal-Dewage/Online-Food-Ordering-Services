package com.ranmal.shoppingcart.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String errorMessage = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).toString();
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                errorMessage.substring(errorMessage.lastIndexOf("[") + 1, errorMessage.lastIndexOf("]")),
                badRequest.getReasonPhrase(),
                badRequest.value());
        return new ResponseEntity<>(apiExceptionResponse, badRequest);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiExceptionResponse> handleEmptyResultDataAccessException(
            EmptyResultDataAccessException emptyResultDataAccessException) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String errorMessage = emptyResultDataAccessException.getMessage();
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                "No resource found for " + errorMessage.substring(errorMessage.lastIndexOf(".") + 1, errorMessage.lastIndexOf("exists")),
                notFound.getReasonPhrase(),
                notFound.value());
        return new ResponseEntity<>(apiExceptionResponse, notFound);
    }

}
