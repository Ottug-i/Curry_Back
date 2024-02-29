package com.ottugi.curry.web.controller;

import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.IS_NEW;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.USER_ID;
import static com.ottugi.curry.TestConstants.VALUE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.auth.AuthService;
import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void 회원_가입_및_로그인() throws Exception {
        // given
        TokenResponseDto tokenResponseDto = new TokenResponseDto(user, VALUE, IS_NEW);
        when(authService.signInOrSignUpAndIssueToken(any(UserSaveRequestDto.class), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        // when, then
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(user.getEmail(), user.getNickName());
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.nickName").value(user.getNickName()))
                .andExpect(jsonPath("$.role").value(user.getRole().getRole()))
                .andExpect(jsonPath("$.token").value(VALUE))
                .andExpect(jsonPath("$.isNew").value(IS_NEW));
    }

    @Test
    void 토큰_재발급() throws Exception {
        // given
        TokenResponseDto tokenResponseDto = new TokenResponseDto(user, VALUE, !IS_NEW);
        when(authService.reissueToken(anyString(), any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        // when, then
        mockMvc.perform(post("/auth/reissue")
                        .param("email", user.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.nickName").value(user.getNickName()))
                .andExpect(jsonPath("$.role").value(user.getRole().getRole()))
                .andExpect(jsonPath("$.token").value(VALUE))
                .andExpect(jsonPath("$.isNew").value(!IS_NEW));
    }
}