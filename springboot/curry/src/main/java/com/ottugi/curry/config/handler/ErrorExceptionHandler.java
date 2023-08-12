package com.ottugi.curry.config.handler;

import com.ottugi.curry.config.exception.BaseException;
import com.ottugi.curry.config.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BaseException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(BaseException e) {
        log.error("exceptionHandler throw BaseException : {} {}", e.getBaseCode(), e.getBaseCode().getMessage());
        return ErrorResponse.toResponseEntity(e.getBaseCode());
    }
}