package com.ranmal.product.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String errorMessage;

    public NotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
