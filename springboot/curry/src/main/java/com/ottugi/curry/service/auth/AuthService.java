package com.ottugi.curry.service.auth;

import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    TokenResponseDto login(UserSaveRequestDto userSaveRequestDto, HttpServletResponse response);
    TokenResponseDto reissueToken(String email, HttpServletRequest request, HttpServletResponse response);
}
