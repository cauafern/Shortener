package com.web.shortener.exception;

public class CodeExpiredException extends RuntimeException{
    public CodeExpiredException(String message) {
        super(message);
    }
}
