package com.ottugi.curry.service.user;

import com.ottugi.curry.config.jwt.TokenProvider;
import com.ottugi.curry.domain.token.Token;
import com.ottugi.curry.domain.token.TokenRepository;
import com.ottugi.curry.domain.user.*;
import com.ottugi.curry.web.dto.user.TokenDto;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserSaveRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    TokenRepository tokenRepository;

    @Value("${jwt.security.key}")
    public String secret;

    @Override
    public TokenDto login(UserSaveRequestDto userSaveRequestDto, HttpServletResponse response) {

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

    @Override
    public TokenDto reissue(String email, HttpServletRequest request, HttpServletResponse response) {

        try {
            Optional<Token> refreshToken = tokenRepository.findById(email);
            if (refreshToken == null) {
                throw new IllegalArgumentException("토큰 재발급에 실패했습니다.");
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

    @Override
    public UserResponseDto getProfile(Long id) {

        User user = findUser(id);
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto setProfile(UserUpdateRequestDto userUpdateRequestDto) {

        User user = findUser(userUpdateRequestDto.getId());
        user.updateProfile(userUpdateRequestDto.getNickName());
        return new UserResponseDto(user);
    }

    @Override
    public Boolean setWithdraw(Long id) {

        User user = findUser(id);
        userRepository.delete(user);
        return true;
    }

    public Boolean validateDuplicatedUser(String email) {

        return userRepository.countByEmail(email) > 0;
    }

    public User findUser(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    public TokenDto createToken(User user, HttpServletResponse response) {

        Token accessToken = tokenProvider.createAccessToken(user);
        Token refreshToken = tokenProvider.createRefreshToken(user);

        tokenProvider.setHeaderAccessToken(response, accessToken.getValue());

        tokenRepository.save(refreshToken);

        return new TokenDto(user, accessToken.getValue());
    }
}
