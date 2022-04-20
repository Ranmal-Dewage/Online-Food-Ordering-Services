package com.ranmal.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiExceptionResponse> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(invalidCredentialsException.getMessage(),
                unauthorized.getReasonPhrase(), unauthorized.value());
        return new ResponseEntity<>(apiExceptionResponse, unauthorized);
    }

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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(
            BadRequestException badRequestException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                badRequestException.getMessage(),
                badRequest.getReasonPhrase(),
                badRequest.value());
        return new ResponseEntity<>(apiExceptionResponse, badRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleAnyOtherException() {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                "The Server encountered an internal error or misconfiguration and was unable to complete the request",
                internalServerError.getReasonPhrase(),
                internalServerError.value());
        return new ResponseEntity<>(apiExceptionResponse, internalServerError);
    }

}
