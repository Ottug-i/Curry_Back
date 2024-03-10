package com.ottugi.curry.web.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserResponseDtoTest {
    public static UserResponseDto initUserResponseDto(User user) {
        return new UserResponseDto(user);
    }

    private User user;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("UserResponseDto 생성 테스트")
    void testUserResponseDto() {
        UserResponseDto userResponseDto = initUserResponseDto(user);

        assertEquals(user.getId(), userResponseDto.getId());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getNickName(), userResponseDto.getNickName());
        assertEquals(user.getRole().getRoleName(), userResponseDto.getRole());
    }
}