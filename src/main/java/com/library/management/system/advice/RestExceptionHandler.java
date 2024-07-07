package com.library.management.system.advice;

import com.library.management.system.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiProblem> handleAuthorNotFoundException(ApiException e) {
        var apiProblem = new ApiProblem();
        apiProblem.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(apiProblem, HttpStatus.valueOf(e.getStatusCode()));
    }
}
