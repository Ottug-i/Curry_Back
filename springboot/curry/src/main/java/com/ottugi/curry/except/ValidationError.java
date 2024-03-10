package com.ottugi.curry.except;

import javax.validation.ConstraintViolation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
@Builder
@RequiredArgsConstructor
public class ValidationError {
    private final String field;
    private final String value;
    private final String message;

    public static ValidationError of(FieldError fieldError) {
        return ValidationError.builder()
                .field(fieldError.getField())
                .value(String.valueOf(fieldError.getRejectedValue()))
                .message(fieldError.getDefaultMessage())
                .build();
    }

    public static ValidationError of(ConstraintViolation violation) {
        return ValidationError.builder()
                .field(getParameterName(violation.getPropertyPath().toString()))
                .value(String.valueOf(violation.getInvalidValue()))
                .message(violation.getMessageTemplate())
                .build();
    }

    public static String getParameterName(String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        return parts[parts.length - 1];
    }
}