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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.ottugi.curry.TestConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private User user;

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void 회원_조회() throws Exception {
        // given
        UserResponseDto userResponseDto = new UserResponseDto(user);
        when(userService.getProfile(anyLong())).thenReturn(userResponseDto);

        // when, then
        mockMvc.perform(get("/api/user")
                        .param("id", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.nickName").value(user.getNickName()))
                .andExpect(jsonPath("$.role").value(user.getRole().getRole()));
    }

    @Test
    void 회원_수정() throws Exception {
        // given
        user.updateProfile(NEW_NICKNAME);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        when(userService.updateProfile(any(UserUpdateRequestDto.class))).thenReturn(userResponseDto);

        // when, then
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(user.getId(), NEW_NICKNAME);
        mockMvc.perform(put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.nickName").value(user.getNickName()))
                .andExpect(jsonPath("$.role").value(user.getRole().getRole()));
    }

    @Test
    void 회원_탈퇴() throws Exception {
        // given
        when(userService.setWithdraw(anyLong())).thenReturn(true);

        // when, then
        mockMvc.perform(delete("/api/user/withdraw")
                        .param("id", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}