package com.library.management.system.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public void initialize(ISBN isbn) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null) {
            return true;
        }
        return validateISBN(isbn);
    }

    private boolean validateISBN(String isbn) {
        return org.apache.commons.validator.routines.ISBNValidator.getInstance().isValid(isbn);
    }
}
