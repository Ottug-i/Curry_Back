package com.ottugi.curry.web.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserSaveRequestDtoTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
    }

    @Test
    @DisplayName("UserSaveRequestDto 생성 테스트")
    void testUserSaveRequestDto() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();

        assertEquals(user.getEmail(), userSaveRequestDto.getEmail());
        assertEquals(user.getNickName(), userSaveRequestDto.getNickName());
    }
}