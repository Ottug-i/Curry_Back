package com.ottugi.curry;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorUtil<T> {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        for (ConstraintViolation<T> violation : violations) {
            System.err.println(violation.getMessage());
        }
    }
}