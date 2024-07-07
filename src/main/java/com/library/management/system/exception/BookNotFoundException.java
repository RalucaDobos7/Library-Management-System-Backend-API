package com.library.management.system.exception;

public class BookNotFoundException extends ApiException {

    public BookNotFoundException(Integer statusCode) {
        super("Book is not present for given id", statusCode);
    }
}
