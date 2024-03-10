package com.ottugi.curry.service.auth;

import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthService {
    /**
     * 회원 가입 또는 로그인을 한 후, JWT 토큰을 발급 받고 사용자 정보를 조회한다.
     *
     * @param requestDto (회원 저장 정보를 담은 DTO)
     * @param response   (HttpServletResponse)
     * @return TokenResponseDto (JWT 토큰과 회원 정보를 담은 DTO)
     */
    TokenResponseDto signUpOrSignInAndIssueToken(UserSaveRequestDto requestDto, HttpServletResponse response);

    /**
     * JWT 토큰을 재발급 받는다.
     *
     * @param email    (회원 이메일)
     * @param request  (HttpServletRequest)
     * @param response (HttpServletResponse)
     * @return TokenResponseDto (JWT 토큰과 회원 정보를 담은 DTO)
     */
    TokenResponseDto reissueToken(String email, HttpServletRequest request, HttpServletResponse response);
}