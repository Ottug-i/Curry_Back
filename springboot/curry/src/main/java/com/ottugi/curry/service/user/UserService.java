package com.ottugi.curry.service.user;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    @Transactional(readOnly = true)
    User findUserByUserId(Long userId);

    @Transactional(readOnly = true)
    User findUserByEmail(String email);

    @Transactional(readOnly = true)
    UserResponseDto findUserProfileByUserId(Long userId);

    UserResponseDto modifyUserProfile(UserUpdateRequestDto updateRequestDto);

    Boolean withdrawUserAccount(Long userId);
}
