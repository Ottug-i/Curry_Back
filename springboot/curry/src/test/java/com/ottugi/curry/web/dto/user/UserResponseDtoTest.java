package com.ottugi.curry.web.dto.user;

import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseDtoTest {

    private User user;

    @Test
    void 회원_응답_Dto_롬복() {
        // given
        user = User.builder()
                .id(USER_ID)
                .email(EMAIL)
                .nickName(NICKNAME)
                .favoriteGenre(FAVORITE_GENRE)
                .role(ROLE)
                .build();
        // when
        UserResponseDto userResponseDto = new UserResponseDto(user);

        // then
        assertEquals(user.getId(), userResponseDto.getId());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getNickName(), userResponseDto.getNickName());
        assertEquals(user.getRole().getRole(), userResponseDto.getRole());
    }
}