package com.ottugi.curry.service.auth;

import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthService {
    TokenResponseDto signUpOrSignInAndIssueToken(UserSaveRequestDto requestDto, HttpServletResponse response);

    TokenResponseDto reissueToken(String email, HttpServletRequest request, HttpServletResponse response);
}
