package com.ottugi.curry.service.auth;

import com.ottugi.curry.config.jwt.TokenProvider;
import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    TokenRepository tokenRepository;

    @Value("${jwt.security.key}")
    public String secret;

    // 회원가입 또는 로그인
    @Override
    public TokenResponseDto login(UserSaveRequestDto userSaveRequestDto, HttpServletResponse response) {

        if(validateDuplicatedUser(userSaveRequestDto.getEmail())) {
            Optional<User> user = userRepository.findByEmail(userSaveRequestDto.getEmail());
            return createToken(user.get(), response);
        }
        else {
            User user = userRepository.save(userSaveRequestDto.toEntity());
            if (user == null) {
                throw new IllegalArgumentException("사용자 저장에 실패했습니다.");
            }
            return createToken(user, response);
        }
    }

    // 토큰 재발급
    @Override
    public TokenResponseDto reissueToken(String email, HttpServletRequest request, HttpServletResponse response) {

        try {
            Optional<Token> refreshToken = tokenRepository.findById(email);
            if (!refreshToken.isPresent()) {
                throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
            }

            boolean isTokenValid = tokenProvider.validateToken(refreshToken.get().getValue(), request);

            if(isTokenValid) {
                Optional<User> user = userRepository.findByEmail(email);

                if(user.isPresent()) {
                    return createToken(user.get(), response);
                }
            }
        } catch(ExpiredJwtException e) {
            throw new IllegalArgumentException("토큰이 만료되었습니다. 다시 로그인하세요.");
        }
        return null;
    }

    // 중복 사용자 검증
    private Boolean validateDuplicatedUser(String email) {

        return userRepository.countByEmail(email) > 0;
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
