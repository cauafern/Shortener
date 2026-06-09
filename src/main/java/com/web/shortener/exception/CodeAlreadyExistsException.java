package com.web.shortener.exception;

public class CodeAlreadyExistsException extends RuntimeException {
    public CodeAlreadyExistsException(String message) {
        super(message);
    }
}
