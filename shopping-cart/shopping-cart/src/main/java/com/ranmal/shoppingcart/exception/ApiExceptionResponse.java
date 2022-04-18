package com.ranmal.shoppingcart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiExceptionResponse {

    private String errorMessage;
    private String error;
    private int status;

}
