package com.terenceapps.spanishverbs.exception;

public class EmailDoesNotExistException extends RuntimeException {

    public EmailDoesNotExistException(String message) {
        super(message);
    }
}
