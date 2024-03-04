package com.ottugi.curry.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.config.SecurityConfig;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.jwt.JwtAuthenticationFilter;
import com.ottugi.curry.service.user.UserService;
import com.ottugi.curry.web.dto.user.UserResponseDto;
import com.ottugi.curry.web.dto.user.UserResponseDtoTest;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDto;
import com.ottugi.curry.web.dto.user.UserUpdateRequestDtoTest;
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

@WebMvcTest(controllers = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)})
@WithMockUser
public class UserControllerTest {
    private User user;
    private UserUpdateRequestDto userUpdateRequestDto;
    private UserResponseDto userResponseDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();

        userUpdateRequestDto = UserUpdateRequestDtoTest.initUserUpdateRequestDto(user);
        userResponseDto = UserResponseDtoTest.initUserResponseDto(user);
    }

    @Test
    @DisplayName("회원 정보 조회 테스트")
    void testUserProfileDetails() throws Exception {
        when(userService.findUserProfileByUserId(anyLong())).thenReturn(userResponseDto);

        mockMvc.perform(get("/api/user")
                        .param("id", String.valueOf(user.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.nickName").value(userResponseDto.getNickName()))
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole()));

        verify(userService, times(1)).findUserProfileByUserId(anyLong());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void testUserProfileModify() throws Exception {
        when(userService.modifyUserProfile(any(UserUpdateRequestDto.class))).thenReturn(userResponseDto);

        mockMvc.perform(put("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponseDto.getId()))
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.nickName").value(userResponseDto.getNickName()))
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole()));

        verify(userService, times(1)).modifyUserProfile(any(UserUpdateRequestDto.class));
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void testUserWithdraw() throws Exception {
        when(userService.withdrawUserAccount(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/user/withdraw")
                        .param("id", String.valueOf(user.getId()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService, times(1)).withdrawUserAccount(anyLong());
    }
}