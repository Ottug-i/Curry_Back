package com.ottugi.curry.service.user;

import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;

public interface UserService {

    UserResponseDto getProfile(Long id);
    UserResponseDto updateProfile(UserUpdateRequestDto userUpdateRequestDto);
    Boolean setWithdraw(Long id);
}
