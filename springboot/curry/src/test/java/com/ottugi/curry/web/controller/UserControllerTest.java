package com.ottugi.curry.web.controller;

import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.NEW_NICKNAME;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
        when(userService.findUserProfileByUserId(anyLong())).thenReturn(userResponseDto);

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
        when(userService.modifyUserProfile(any(UserUpdateRequestDto.class))).thenReturn(userResponseDto);

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
        when(userService.withdrawUserAccount(anyLong())).thenReturn(true);

        // when, then
        mockMvc.perform(delete("/api/user/withdraw")
                        .param("id", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}