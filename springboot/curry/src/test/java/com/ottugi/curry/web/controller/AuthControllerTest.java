package com.ottugi.curry.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.auth.AuthService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.TokenResponseDtoTest;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDtoTest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AuthController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@WithMockUser
class AuthControllerTest {
    private User user;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("회원 가입 또는 로그인 후 토큰 발급 테스트")
    void testSignUpOrSignIn() throws Exception {
        TokenResponseDto tokenResponseDto = TokenResponseDtoTest.initTokenResponseDto(user);
        when(authService.signUpOrSignInAndIssueToken(any(UserSaveRequestDto.class), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.initUserSaveRequestDto(user);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tokenResponseDto.getId()))
                .andExpect(jsonPath("$.email").value(tokenResponseDto.getEmail()))
                .andExpect(jsonPath("$.nickName").value(tokenResponseDto.getNickName()))
                .andExpect(jsonPath("$.role").value(tokenResponseDto.getRole()))
                .andExpect(jsonPath("$.token").value(tokenResponseDto.getToken()))
                .andExpect(jsonPath("$.isNew").value(tokenResponseDto.getIsNew()));

        verify(authService, times(1)).signUpOrSignInAndIssueToken(any(UserSaveRequestDto.class), any(HttpServletResponse.class));
    }

    @Test
    @DisplayName("토큰 재발급 테스트")
    void testReissueToken() throws Exception {
        TokenResponseDto tokenResponseDto = TokenResponseDtoTest.initTokenResponseDto(user);
        when(authService.reissueToken(anyString(), any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        mockMvc.perform(post("/auth/reissue")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tokenResponseDto.getId()))
                .andExpect(jsonPath("$.email").value(tokenResponseDto.getEmail()))
                .andExpect(jsonPath("$.nickName").value(tokenResponseDto.getNickName()))
                .andExpect(jsonPath("$.role").value(tokenResponseDto.getRole()))
                .andExpect(jsonPath("$.token").value(tokenResponseDto.getToken()))
                .andExpect(jsonPath("$.isNew").value(tokenResponseDto.getIsNew()));

        verify(authService, times(1)).reissueToken(anyString(), any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
}