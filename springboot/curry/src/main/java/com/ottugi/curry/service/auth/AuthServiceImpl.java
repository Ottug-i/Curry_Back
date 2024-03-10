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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public TokenResponseDto signUpOrSignInAndIssueToken(UserSaveRequestDto requestDto, HttpServletResponse response) {
        User user = findOrAddUser(requestDto);
        return issueToken(user, response);
    }

    @Override
    public TokenResponseDto reissueToken(String email, HttpServletRequest request, HttpServletResponse response) {
        Token refreshToken = findRefreshTokenByEmail(email);
        validateRefreshToken(refreshToken, request);
        User user = userService.findUserByEmail(email);
        return issueToken(user, response);
    }

    private User findOrAddUser(UserSaveRequestDto requestDto) {
        String email = requestDto.getEmail();
        return userRepository.findByEmail(email).map(user -> {
            user.markAsExistingUser();
            return user;
        }).orElseGet(() -> userRepository.save(requestDto.toEntity()));
    }

    private Token findRefreshTokenByEmail(String email) {
        return tokenRepository.findById(email)
                .orElseThrow(() -> new JwtAuthenticationException(BaseCode.JWT_REFRESH_TOKEN_EXPIRED));
    }

    private void validateRefreshToken(Token refreshToken, HttpServletRequest request) {
        if (!tokenProvider.validateToken(refreshToken.getValue(), request)) {
            throw new JwtAuthenticationException(BaseCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }

    private TokenResponseDto issueToken(User user, HttpServletResponse response) {
        Token accessToken = tokenProvider.createAccessToken(user);
        Token refreshToken = tokenProvider.createRefreshToken(user);
        tokenProvider.setHeaderAccessToken(response, accessToken.getValue());
        tokenRepository.save(refreshToken);
        return new TokenResponseDto(user, accessToken.getValue());
    }
}
