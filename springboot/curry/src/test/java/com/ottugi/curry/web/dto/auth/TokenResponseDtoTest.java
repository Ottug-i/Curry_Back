package com.ottugi.curry.web.dto.auth;

import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class TokenResponseDtoTest {

    private User user;

    @Test
    void TokenDto_롬복() {
        // given
        user = User.builder()
                .email(EMAIL)
                .nickName(NICKNAME)
                .favoriteGenre(FAVORITE_GENRE)
                .role(ROLE)
                .build();

        // when
        TokenResponseDto tokenResponseDto = new TokenResponseDto(user, VALUE);

        // then
        assertEquals(tokenResponseDto.getId(), USER_ID);
        assertEquals(tokenResponseDto.getEmail(), EMAIL);
        assertEquals(tokenResponseDto.getNickName(), NICKNAME);
        assertEquals(tokenResponseDto.getRole(), ROLE);
        assertEquals(tokenResponseDto.getToken(), VALUE);
    }
}