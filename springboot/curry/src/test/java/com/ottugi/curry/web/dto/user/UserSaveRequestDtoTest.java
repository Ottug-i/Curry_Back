package com.ottugi.curry.web.dto.user;

import com.ottugi.curry.web.dto.auth.UserSaveRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSaveRequestDtoTest {

    @Test
    void UserSaveRequestDto_롬복() {

        // given
        String email = "wn8925@sookmyung.ac.kr";
        String nickName = "가경";

        // when
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto(email, nickName);

        // then
        assertEquals(userSaveRequestDto.getEmail(), email);
        assertEquals(userSaveRequestDto.getNickName(), nickName);
    }
}