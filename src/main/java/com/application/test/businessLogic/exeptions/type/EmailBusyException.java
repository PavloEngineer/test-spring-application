package com.application.test.businessLogic.exeptions.type;

public class EmailBusyException extends RuntimeException {

    public EmailBusyException(String message) {
        super(message);
    }

    public EmailBusyException(String message, Throwable cause) {
        super(message, cause);
    }
}
