package com.ottugi.curry.except;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
    private List<ValidationError> validation;

    public ErrorResponse(BaseCode baseCode) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = baseCode.getHttpStatus().value();
        this.error = baseCode.getHttpStatus().name();
        this.code = baseCode.name();
        this.message = baseCode.getMessage();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(BaseCode baseCode) {
        return ResponseEntity
                .status(baseCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .status(baseCode.getHttpStatus().value())
                        .error(baseCode.getHttpStatus().name())
                        .code(baseCode.name())
                        .message(baseCode.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntityWithValidation(BaseCode baseCode, List<ValidationError> errors) {
        return ResponseEntity
                .status(baseCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .status(baseCode.getHttpStatus().value())
                        .error(baseCode.getHttpStatus().name())
                        .code(baseCode.name())
                        .message(baseCode.getMessage())
                        .validation(errors)
                        .build()
                );
    }

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
