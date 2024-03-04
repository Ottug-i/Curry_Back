package com.ottugi.curry.web.dto.user;

import static com.ottugi.curry.TestConstants.NEW_NICKNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserUpdateRequestDtoTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
    }

    @Test
    @DisplayName("UserUpdateRequestDto 생성 테스트")
    void testUserUpdateRequestDto() {
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .id(user.getId())
                .nickName(NEW_NICKNAME)
                .build();

        assertEquals(user.getId(), userUpdateRequestDto.getId());
        assertEquals(NEW_NICKNAME, userUpdateRequestDto.getNickName());
    }
}