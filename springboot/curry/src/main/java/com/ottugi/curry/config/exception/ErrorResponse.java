package com.ottugi.curry.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String error;
    private String code;
    private String message;

    public ErrorResponse(BaseCode baseCode) {
        this.status = baseCode.getHttpStatus().value();
        this.error = baseCode.getHttpStatus().name();
        this.code = baseCode.name();
        this.message = baseCode.getMessage();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(BaseCode baseCode) {
        return ResponseEntity
                .status(baseCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(baseCode.getHttpStatus().value())
                        .error(baseCode.getHttpStatus().name())
                        .code(baseCode.name())
                        .message(baseCode.getMessage())
                        .build()
                );
    }
}
