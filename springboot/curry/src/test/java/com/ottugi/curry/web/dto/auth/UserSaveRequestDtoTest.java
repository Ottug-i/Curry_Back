package com.ottugi.curry.web.dto.auth;

import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserSaveRequestDtoTest {

    @Test
    void 회원_저장_요청_Dto_롬복() {
        // when
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(EMAIL, NICKNAME);

        // then
        assertEquals(EMAIL, userSaveRequestDto.getEmail());
        assertEquals(NICKNAME, userSaveRequestDto.getNickName());
    }
}