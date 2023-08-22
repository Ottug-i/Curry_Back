package com.ottugi.curry.web.dto.auth;

import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class TokenResponseDtoTest {

    private User user;

    @Test
    void 토큰_응답_Dto_롬복() {
        // given
        user = User.builder()
                .id(USER_ID)
                .email(EMAIL)
                .nickName(NICKNAME)
                .favoriteGenre(FAVORITE_GENRE)
                .role(ROLE)
                .build();

        // when
        TokenResponseDto tokenResponseDto = new TokenResponseDto(user, VALUE, IS_NEW);

        // then
        assertEquals(user.getId(), tokenResponseDto.getId());
        assertEquals(user.getEmail(), tokenResponseDto.getEmail());
        assertEquals(user.getNickName(), tokenResponseDto.getNickName());
        assertEquals(user.getRole().getRole(), tokenResponseDto.getRole());
        assertEquals(VALUE, tokenResponseDto.getToken());
        assertEquals(IS_NEW, tokenResponseDto.getIsNew());
    }
}