package com.library.management.system.exception;

public class InvalidAuthorException extends ApiException {

    public InvalidAuthorException(Integer statusCode) {
        super("Author is not present for given id", statusCode);
    }

    public InvalidAuthorException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
