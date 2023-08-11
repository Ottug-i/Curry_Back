package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.*;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 회원 정보 조회
    @Override
    public UserResponseDto getProfile(Long id) {

        User user = findUser(id);
        return new UserResponseDto(user);
    }

    // 회원 정보 수정
    @Override
    public UserResponseDto updateProfile(UserUpdateRequestDto userUpdateRequestDto) {

        User user = findUser(userUpdateRequestDto.getId());
        user.updateProfile(userUpdateRequestDto.getNickName());
        return new UserResponseDto(user);
    }

    // 회원 탈퇴
    @Override
    public Boolean setWithdraw(Long id) {

        User user = findUser(id);
        userRepository.delete(user);
        return true;
    }

    // 회원 조회
    public User findUser(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }
}
