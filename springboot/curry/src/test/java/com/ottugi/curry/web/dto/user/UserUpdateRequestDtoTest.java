package com.ottugi.curry.web.dto.user;

import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserUpdateRequestDtoTest {

    @Test
    void UserUpdateRequestDto_롬복() {
        // when
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(USER_ID, NICKNAME);

        // then
        assertEquals(userUpdateRequestDto.getId(), USER_ID);
        assertEquals(userUpdateRequestDto.getNickName(), NICKNAME);
    }
}