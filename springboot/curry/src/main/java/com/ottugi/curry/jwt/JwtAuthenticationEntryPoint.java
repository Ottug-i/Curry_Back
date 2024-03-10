package com.ottugi.curry.jwt;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(BaseCode.JWT_UNAUTHORIZED);
        response.getWriter().write(errorResponse.convertToJson());
        response.setStatus(errorResponse.getStatus());
    }
}