package com.ottugi.curry.web.dto.user;

import static com.ottugi.curry.domain.user.UserTest.NEW_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserUpdateRequestDtoTest {
    public static UserUpdateRequestDto initUserUpdateRequestDto(User user) {
        return UserUpdateRequestDto.builder()
                .id(user.getId())
                .nickName(NEW_NICKNAME)
                .build();
    }

    private User user;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("UserUpdateRequestDto 생성 테스트")
    void testUserUpdateRequestDto() {
        UserUpdateRequestDto userUpdateRequestDto = initUserUpdateRequestDto(user);

        assertEquals(user.getId(), userUpdateRequestDto.getId());
        assertEquals(NEW_NICKNAME, userUpdateRequestDto.getNickName());
    }
}