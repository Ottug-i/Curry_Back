package com.ottugi.curry.service.auth;

import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.JwtAuthenticationException;
import com.ottugi.curry.jwt.TokenProvider;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Boolean NEW_USER = true;
    private final Boolean EXISTING_USER = false;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public TokenResponseDto singUpOrLogin(UserSaveRequestDto requestDto, HttpServletResponse response) {
        if (isDuplicatedUser(requestDto.getEmail())) {
            return loginAndIssueToken(requestDto.getEmail(), response);
        }
        return signUpAndIssueToken(requestDto, response);
    }

    @Override
    public TokenResponseDto reissueToken(String email, HttpServletRequest request, HttpServletResponse response) {
        try {
            Token refreshToken = findToken(email);
            validateToken(refreshToken, request);
            return loginAndIssueToken(email, response);
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(BaseCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }

    private Boolean isDuplicatedUser(String email) {
        return userRepository.existsByEmail(email);
    }

    private TokenResponseDto loginAndIssueToken(String email, HttpServletResponse response) {
        User existingUser = userService.findUserByEmail(email);
        return createToken(existingUser, response, EXISTING_USER);
    }

    private TokenResponseDto signUpAndIssueToken(UserSaveRequestDto requestDto, HttpServletResponse response) {
        User newUser = userRepository.save(requestDto.toEntity());
        return createToken(newUser, response, NEW_USER);
    }

    private Token findToken(String email) {
        return tokenRepository.findById(email).orElseThrow(() -> new JwtAuthenticationException(BaseCode.JWT_REFRESH_TOKEN_EXPIRED));
    }

    private void validateToken(Token refreshToken, HttpServletRequest request) {
        if (!tokenProvider.validateToken(refreshToken.getValue(), request)) {
            throw new JwtAuthenticationException(BaseCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }

    private TokenResponseDto createToken(User user, HttpServletResponse response, Boolean isNewUser) {
        Token accessToken = tokenProvider.createAccessToken(user);
        Token refreshToken = tokenProvider.createRefreshToken(user);
        tokenProvider.setHeaderAccessToken(response, accessToken.getValue());
        tokenRepository.save(refreshToken);
        return new TokenResponseDto(user, accessToken.getValue(), isNewUser);
    }
}
