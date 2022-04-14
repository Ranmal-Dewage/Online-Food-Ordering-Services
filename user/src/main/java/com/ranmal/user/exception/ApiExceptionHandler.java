package com.ranmal.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(sqlIntegrityConstraintViolationException.getMessage(),
                conflict.getReasonPhrase(), conflict.value());
        return new ResponseEntity<>(apiExceptionResponse, conflict);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundException notFoundException) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(notFoundException.getMessage(),
                notFound.getReasonPhrase(), notFound.value());
        return new ResponseEntity<>(apiExceptionResponse, notFound);
    }

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

}
