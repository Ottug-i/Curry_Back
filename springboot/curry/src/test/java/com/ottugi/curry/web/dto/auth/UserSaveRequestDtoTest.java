package com.ottugi.curry.web.dto.auth;

import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserSaveRequestDtoTest {

    @Test
    void UserSaveRequestDto_롬복() {
        // when
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(EMAIL, NICKNAME);

        // then
        assertEquals(userSaveRequestDto.getEmail(), EMAIL);
        assertEquals(userSaveRequestDto.getNickName(), NICKNAME);
    }
}