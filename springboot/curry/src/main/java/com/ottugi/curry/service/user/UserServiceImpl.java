package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.except.BaseCode;
import com.ottugi.curry.except.NotFoundException;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(BaseCode.USER_NOT_FOUND));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(BaseCode.USER_NOT_FOUND));
    }

    @Override
    public UserResponseDto findUserProfileByUserId(Long userId) {
        User user = findUserByUserId(userId);
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto modifyUserProfile(UserUpdateRequestDto updateRequestDto) {
        User user = findUserByUserId(updateRequestDto.getId());
        user.updateProfile(updateRequestDto.getNickName());
        return new UserResponseDto(user);
    }

    @Override
    public boolean withdrawUserAccount(Long userId) {
        User user = findUserByUserId(userId);
        user.withdrawUser();
        return true;
    }
}
