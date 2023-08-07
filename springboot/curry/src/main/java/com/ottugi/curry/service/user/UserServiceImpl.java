package com.ottugi.curry.service.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ottugi.curry.domain.user.Role;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.user.TokenDto;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserSaveRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.expiration_time}")
    public int expiration_time;

    @Override
    public TokenDto login(UserSaveRequestDto userSaveRequestDto) {

        if(validateDuplicatedUser(userSaveRequestDto.getEmail())) {
            User user = userRepository.findByEmail(userSaveRequestDto.getEmail());
            return createToken(user);
        }
        else {
            User user = userRepository.save(userSaveRequestDto.toEntity());
            if (user == null) {
                throw new IllegalArgumentException("사용자 저장에 실패했습니다.");
            }
            return createToken(user);
        }
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

    public TokenDto createToken(User user) {
        String jwtToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ expiration_time))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().getRole())
                .sign(Algorithm.HMAC512(secret));

        return new TokenDto(user, jwtToken);
    }
}
