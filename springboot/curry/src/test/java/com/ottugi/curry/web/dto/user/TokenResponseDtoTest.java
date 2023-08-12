package com.ottugi.curry.web.dto.user;

import com.ottugi.curry.web.dto.auth.TokenResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

class TokenResponseDtoTest {

    @Value("${jwt.secret}")
    private String secret;

    @Test
    void TokenDto_롬복() {

        // given
        Long id = 1L;
        String email = "wn8925@sookmyung.ac.kr";
        String nickName = "가경";

        // when
        TokenResponseDto tokenResponseDto = new TokenResponseDto(id, email, nickName, secret);

        // then
        assertEquals(tokenResponseDto.getId(), id);
        assertEquals(tokenResponseDto.getEmail(), email);
        assertEquals(tokenResponseDto.getNickName(), nickName);
        assertEquals(tokenResponseDto.getToken(), secret);
    }
}