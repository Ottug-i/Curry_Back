package com.ottugi.curry.service.auth;

import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.BaseException;
import com.ottugi.curry.jwt.TokenProvider;
import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final CommonService commonService;
    private final TokenProvider tokenProvider;

    // 회원가입 또는 로그인
    @Override
    @Transactional
    public TokenResponseDto login(UserSaveRequestDto userSaveRequestDto, HttpServletResponse response) {
        if(isDuplicatedUser(userSaveRequestDto.getEmail())) {
            User user = commonService.findByUserEmail(userSaveRequestDto.getEmail());
            return createToken(user, response);
        } else {
            User newUser = userRepository.save(userSaveRequestDto.toEntity());
            return createToken(newUser, response);
        }
    }

    // 토큰 재발급
    @Override
    @Transactional
    public TokenResponseDto reissueToken(String email, HttpServletRequest request, HttpServletResponse response) {
        try {
            Token refreshToken = findToken(email);
            validateToken(refreshToken, request);
            User user = commonService.findByUserEmail(email);
            return createToken(user, response);
        } catch (ExpiredJwtException e) {
            throw new BaseException(BaseCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }

    // 중복 사용자 검증
    private Boolean isDuplicatedUser(String email) {
        return userRepository.existsByEmail(email);
    }

    // 리프레시 토큰 찾기
    private Token findToken(String email) {
        return tokenRepository.findById(email).orElseThrow(() -> new BaseException(BaseCode.JWT_UNAUTHORIZED));
    }
    
    // 토큰 유효성 검증
    private void validateToken(Token refreshToken, HttpServletRequest request) {
        if (!tokenProvider.validateToken(refreshToken.getValue(), request)) {
            throw new BaseException(BaseCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }

    // 토큰 생성 및 저장
    private TokenResponseDto createToken(User user, HttpServletResponse response) {
        Token accessToken = tokenProvider.createAccessToken(user);
        Token refreshToken = tokenProvider.createRefreshToken(user);
        tokenProvider.setHeaderAccessToken(response, accessToken.getValue());
        tokenRepository.save(refreshToken);
        return new TokenResponseDto(user, accessToken.getValue());
    }
}
