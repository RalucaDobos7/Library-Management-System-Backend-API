package com.library.management.system.exception;

public class InvalidAuthorException extends ApiException {

    public InvalidAuthorException(Integer statusCode) {
        super("Author does not exist for given id", statusCode);
    }

    public InvalidAuthorException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
