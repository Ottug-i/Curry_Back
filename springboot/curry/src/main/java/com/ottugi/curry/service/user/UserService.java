package com.ottugi.curry.service.user;

import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserResponseDto getProfile(Long id);
    UserResponseDto updateProfile(UserUpdateRequestDto userUpdateRequestDto);
    Boolean setWithdraw(Long id);
}
