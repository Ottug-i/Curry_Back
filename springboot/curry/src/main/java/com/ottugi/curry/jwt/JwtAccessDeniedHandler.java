package com.ottugi.curry.jwt;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(BaseCode.JWT_FORBIDDEN);
        response.getWriter().write(errorResponse.convertToJson());
        response.setStatus(errorResponse.getStatus());
    }
}