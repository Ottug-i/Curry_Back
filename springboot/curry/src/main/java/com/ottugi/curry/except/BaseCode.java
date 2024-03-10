package com.ottugi.curry.except;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseCode {
    SUCCESS(HttpStatus.OK, "정상적으로 처리되었습니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    JWT_FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    JWT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다."),
    JWT_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),

    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피 정보를 찾을 수 없습니다."),

    GENRE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피 장르 정보를 찾을 수 없습니다."),

    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 북마크 정보를 찾을 수 없습니다."),

    RATING_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 평점 정보를 찾을 수 없습니다."),

    RECOMMEND_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 추천 레시피 정보를 찾을 수 없습니다."),

    SEVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 오류가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}