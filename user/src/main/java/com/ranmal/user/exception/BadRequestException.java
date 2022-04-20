package com.ranmal.user.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

    private final String errorMessage;

    public BadRequestException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

}
