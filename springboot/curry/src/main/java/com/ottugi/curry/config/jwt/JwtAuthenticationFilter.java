package com.ottugi.curry.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.exception.BaseCode;
import com.ottugi.curry.config.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final TokenProvider tokenProvider;

    public JwtAuthenticationFilter(TokenProvider provider) {
        tokenProvider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getServletPath();

            if(path.startsWith("/auth")) {
                filterChain.doFilter(request, response);
            } else {
                String accessToken = tokenProvider.resolveAccessToken(request);
                boolean isTokenValid = tokenProvider.validateToken(accessToken, request);

                if (StringUtils.hasText(accessToken) && isTokenValid) {
                    this.setAuthentication(accessToken);
                }

                filterChain.doFilter(request, response);
            }

        } catch (ExpiredJwtException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            ErrorResponse errorResponse = new ErrorResponse(BaseCode.JWT_ACCESS_TOKEN_EXPIRED);
            String result = objectMapper.writeValueAsString(errorResponse);
            response.setStatus(errorResponse.getStatus());
            response.getWriter().write(result);
        }
    }

    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}