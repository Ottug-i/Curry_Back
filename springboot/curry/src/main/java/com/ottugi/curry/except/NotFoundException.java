package com.ottugi.curry.except;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends IllegalArgumentException {
    private final BaseCode baseCode;
}