package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.user.UserServiceImpl;
import com.ottugi.curry.web.dto.user.TokenDto;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserSaveRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final Long userId = 1L;
    private final String email = "wn8925@gmail.com";
    private final String nickName = "가경";
    private final String newNickName = "가경이";

    @Value("${jwt.secret}")
    private String secret;

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void 회원가입_로그인() throws Exception {

        // given
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(email, nickName);
        TokenDto tokenDto = new TokenDto(userId, email, nickName, secret);

        // when
        when(userService.login(any(UserSaveRequestDto.class))).thenReturn(tokenDto);

        // then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickName").value(nickName))
                .andExpect(jsonPath("$.token").value(secret));
    }

    @Test
    void 회원조회() throws Exception {

        // given
        User user = new User(email, nickName);
        UserResponseDto userResponseDto = new UserResponseDto(user);

        // when
        when(userService.getProfile(anyLong())).thenReturn(userResponseDto);

        // Then
        mockMvc.perform(get("/api/user/getProfile")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickName").value(nickName));
    }

    @Test
    void 회원수정() throws Exception {

        // given
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(userId, newNickName);

        User changingUser = new User(email, newNickName);
        UserResponseDto userResponseDto = new UserResponseDto(changingUser);

        // when
        when(userService.setProfile(any(UserUpdateRequestDto.class))).thenReturn(userResponseDto);

        // then
        mockMvc.perform(put("/api/user/setProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(changingUser.getId()))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickName").value(newNickName));
    }

    @Test
    void 탈퇴() throws Exception {

        // given, when
        when(userService.setWithdraw(anyLong())).thenReturn(true);

        // then
        mockMvc.perform(delete("/api/user/setWithdraw")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}