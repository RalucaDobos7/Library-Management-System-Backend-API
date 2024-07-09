package com.library.management.system.exception;

public class InvalidBookException extends ApiException {

    public InvalidBookException(Integer statusCode) {
        super("Book is not present for given id", statusCode);
    }
}
