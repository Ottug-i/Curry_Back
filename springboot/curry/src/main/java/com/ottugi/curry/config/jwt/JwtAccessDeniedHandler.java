package com.ottugi.curry.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.exception.BaseCode;
import com.ottugi.curry.config.exception.ErrorResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        ErrorResponse errorResponse = new ErrorResponse(BaseCode.JWT_FORBIDDEN);
        String result = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(errorResponse.getStatus());
        response.getWriter().write(result);
    }
}