package com.ottugi.curry.except;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtAuthenticationException extends RuntimeException {
    private final BaseCode baseCode;
}
