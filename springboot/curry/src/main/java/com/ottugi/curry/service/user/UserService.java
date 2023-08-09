package com.ottugi.curry.service.user;

import com.ottugi.curry.web.dto.user.TokenDto;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserSaveRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    TokenDto login(UserSaveRequestDto userSaveRequestDto, HttpServletResponse response);
    TokenDto reissue(String email, HttpServletRequest request, HttpServletResponse response);
    UserResponseDto getProfile(Long id);
    UserResponseDto setProfile(UserUpdateRequestDto userUpdateRequestDto);
    Boolean setWithdraw(Long id);
}
