package com.library.management.system.exception;

public class InvalidBookException extends ApiException {

    public InvalidBookException(Integer statusCode) {
        super("Book does not exist for given id", statusCode);
    }
}
