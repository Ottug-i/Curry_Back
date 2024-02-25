package com.ottugi.curry.handler;

import static com.ottugi.curry.except.ValidationError.getParameterName;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.BaseException;
import com.ottugi.curry.except.ErrorResponse;
import com.ottugi.curry.except.JwtAuthenticationException;
import com.ottugi.curry.except.NotFoundException;
import com.ottugi.curry.except.ValidationError;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorExceptionHandler {
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleCustomBaseException(BaseException e) {
        log.error("exceptionHandler throw BaseException : {} {}", e.getBaseCode(), e.getBaseCode().getMessage());
        return ErrorResponse.toResponseEntity(e.getBaseCode());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        log.error("exceptionHandler throw BaseException : {} {}", e.getBaseCode(), e.getBaseCode().getMessage());
        return ErrorResponse.toResponseEntity(e.getBaseCode());
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    protected ResponseEntity<ErrorResponse> handleJwtAccessDeniedException(JwtAuthenticationException e) {
        log.error("exceptionHandler throw BaseException : {} {}", e.getBaseCode(), e.getBaseCode().getMessage());
        return ErrorResponse.toResponseEntity(e.getBaseCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            log.error("name : {}, message : {}", fieldError.getField(), fieldError.getDefaultMessage());
            ValidationError error = ValidationError.of(fieldError);
            errors.add(error);
        }
        return ErrorResponse.toResponseEntityWithValidation(BaseCode.BAD_REQUEST, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            String paramName = getParameterName(constraintViolation.getPropertyPath().toString());
            log.error("name : {}, message : {}", paramName, constraintViolation.getMessageTemplate());
            ValidationError error = ValidationError.of(constraintViolation);
            errors.add(error);
        }
        return ErrorResponse.toResponseEntityWithValidation(BaseCode.BAD_REQUEST, errors);
    }
}