package com.ranmal.shoppingcart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiExceptionResponse> handleMethodArgumentTypeMismatchException(){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                "Provided Invalid Type for a Numeric Parameter",
                badRequest.getReasonPhrase(),
                badRequest.value());
        return new ResponseEntity<>(apiExceptionResponse, badRequest);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionResponse> handleHttpMessageNotReadableException(){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                "Provided Invalid Type for a Numeric Parameter in Request Body",
                badRequest.getReasonPhrase(),
                badRequest.value());
        return new ResponseEntity<>(apiExceptionResponse, badRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleAnyOtherException(){
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                "The Server encountered an internal error or misconfiguration and was unable to complete the request",
                internalServerError.getReasonPhrase(),
                internalServerError.value());
        return new ResponseEntity<>(apiExceptionResponse, internalServerError);
    }

}
