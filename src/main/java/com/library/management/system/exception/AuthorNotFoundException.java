package com.library.management.system.exception;

public class AuthorNotFoundException extends ApiException {

    public AuthorNotFoundException(Integer statusCode) {
        super("Author is not present for given id", statusCode);
    }

    public AuthorNotFoundException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
