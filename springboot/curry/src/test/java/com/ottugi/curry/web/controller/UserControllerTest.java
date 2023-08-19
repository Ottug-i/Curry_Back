package com.ottugi.curry.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.ottugi.curry.TestConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private User user;
    private User changingUser;

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        changingUser = new User(USER_ID, EMAIL, NEW_NICKNAME, FAVORITE_GENRE, ROLE);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void 회원조회() throws Exception {
        // given
        UserResponseDto userResponseDto = new UserResponseDto(user);

        // when
        when(userService.getProfile(USER_ID)).thenReturn(userResponseDto);

        // Then
        mockMvc.perform(get("/api/user")
                        .param("id", String.valueOf(USER_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.nickName").value(NICKNAME))
                .andExpect(jsonPath("$.role").value(ROLE));
    }

    @Test
    void 회원수정() throws Exception {
        // given
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(USER_ID, NEW_NICKNAME);

        UserResponseDto userResponseDto = new UserResponseDto(changingUser);

        // when
        when(userService.updateProfile(userUpdateRequestDto)).thenReturn(userResponseDto);

        // then
        mockMvc.perform(put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(changingUser.getId()))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.nickName").value(NEW_NICKNAME))
                .andExpect(jsonPath("$.role").value(ROLE));
    }

    @Test
    void 탈퇴() throws Exception {
        // given, when
        when(userService.setWithdraw(USER_ID)).thenReturn(true);

        // then
        mockMvc.perform(delete("/api/user/withdraw")
                        .param("id", String.valueOf(USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}