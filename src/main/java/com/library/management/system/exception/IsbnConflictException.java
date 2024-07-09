package com.library.management.system.exception;

public class IsbnConflictException extends ApiException {

    public IsbnConflictException(Integer statusCode) {
        super("Given ISBN already exists", statusCode);
    }
}
