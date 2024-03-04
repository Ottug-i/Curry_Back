package com.ottugi.curry.web.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserResponseDtoTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
    }

    @Test
    @DisplayName("UserResponseDto 생성 테스트")
    void testUserResponseDto() {
        UserResponseDto userResponseDto = new UserResponseDto(user);

        assertEquals(user.getId(), userResponseDto.getId());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getNickName(), userResponseDto.getNickName());
        assertEquals(user.getRole().getRole(), userResponseDto.getRole());
    }
}