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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(
            NotFoundException notFoundException) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                notFoundException.getMessage(),
                notFound.getReasonPhrase(),
                notFound.value());
        return new ResponseEntity<>(apiExceptionResponse, notFound);
    }

}
