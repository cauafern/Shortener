package com.web.shortener.exception;

public class LinkDisabledException extends RuntimeException{
    public LinkDisabledException(String message) {
        super(message);
    }
}
