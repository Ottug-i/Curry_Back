package com.ottugi.curry.except;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum BaseCode {

    // SUCCESS
    SUCCESS(OK, "정상적으로 처리되었습니다."),

    // JWT
    JWT_FORBIDDEN(FORBIDDEN, "접근 권한이 없습니다."),
    JWT_UNAUTHORIZED(UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    JWT_ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, "액세스 토큰이 만료되었습니다."),
    JWT_REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),

    // USER
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),

    // RECIPE
    RECIPE_NOT_FOUND(NOT_FOUND, "해당 레시피 정보를 찾을 수 없습니다."),

    // BOOKMARK
    BOOKMARK_NOT_FOUND(NOT_FOUND, "해당 북마크 정보를 찾을 수 없습니다."),

    // RECOMMEND
    RECOMMEND_NOT_FOUND(NOT_FOUND, "해당 추천 레시피 정보를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}