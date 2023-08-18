package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.auth.AuthService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ottugi.curry.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    private User user;

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        user = new User(EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void 회원가입_로그인() throws Exception {
        // given
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(EMAIL, NICKNAME);
        TokenResponseDto tokenResponseDto = new TokenResponseDto(user, VALUE);

        // when
        when(authService.login(userSaveRequestDto, any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        // then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.nickName").value(NICKNAME))
                .andExpect(jsonPath("$.role").value(ROLE))
                .andExpect(jsonPath("$.token").value(VALUE));
    }

    @Test
    void 토큰재발급() throws Exception {
        // given
        TokenResponseDto tokenResponseDto = new TokenResponseDto(user, VALUE);

        // when
        when(authService.reissueToken(EMAIL, any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        // then
        mockMvc.perform(post("/auth/reissue")
                        .param("email", EMAIL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.nickName").value(NICKNAME))
                .andExpect(jsonPath("$.role").value(ROLE))
                .andExpect(jsonPath("$.token").value(VALUE));
    }
}