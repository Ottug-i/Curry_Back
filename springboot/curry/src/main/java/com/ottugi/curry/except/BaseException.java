package com.ottugi.curry.except;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final BaseCode baseCode;
}
