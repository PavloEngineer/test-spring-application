package com.application.test.businessLogic.exeptions.type;

public class RequestValidationException extends RuntimeException {

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
