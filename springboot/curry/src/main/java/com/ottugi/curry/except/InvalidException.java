package com.ottugi.curry.except;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidException extends RuntimeException {
    private final BaseCode baseCode;
}
