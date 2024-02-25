package com.ottugi.curry.except;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends IllegalArgumentException {
    private final BaseCode baseCode;
}