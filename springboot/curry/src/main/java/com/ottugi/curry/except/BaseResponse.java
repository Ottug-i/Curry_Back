package com.ottugi.curry.except;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseResponse<T> {
    private String message;
    private T data;

    @Builder
    public BaseResponse(BaseCode baseCode, T data) {
        this.message = baseCode.getMessage();
        this.data = data;
    }
}