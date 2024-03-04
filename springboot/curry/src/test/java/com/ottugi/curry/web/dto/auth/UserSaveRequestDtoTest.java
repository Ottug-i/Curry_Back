package com.ottugi.curry.web.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserSaveRequestDtoTest {
    public static UserSaveRequestDto initUserSaveRequestDto(User user) {
        return UserSaveRequestDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }

    private User user;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("UserSaveRequestDto 생성 테스트")
    void testUserSaveRequestDto() {
        UserSaveRequestDto userSaveRequestDto = initUserSaveRequestDto(user);

        assertEquals(user.getEmail(), userSaveRequestDto.getEmail());
        assertEquals(user.getNickName(), userSaveRequestDto.getNickName());
    }
}