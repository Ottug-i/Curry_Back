package com.ottugi.curry.jwt;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getServletPath();
            if (path.startsWith("/auth")) {
                filterChain.doFilter(request, response);
            } else {
                String accessToken = tokenValidator.resolveAccessToken(request);
                boolean isTokenValid = tokenValidator.validateToken(accessToken, request);
                if (StringUtils.hasText(accessToken) && isTokenValid) {
                    this.setAuthentication(accessToken);
                }
                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            response.setContentType("application/json; charset=UTF-8");
            ErrorResponse errorResponse = new ErrorResponse(BaseCode.JWT_ACCESS_TOKEN_EXPIRED);
            response.getWriter().write(errorResponse.convertToJson());
            response.setStatus(errorResponse.getStatus());
        }
    }

    private void setAuthentication(String token) {
        Authentication authentication = tokenValidator.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}