package com.ranmal.user.exception;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends RuntimeException {

    private final String errorMessage;

    public InvalidCredentialsException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

}
