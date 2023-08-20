package com.ottugi.curry.web.dto.user;

import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseDtoTest {

    private User user;

    @Test
    void UserResponseDto_롬복() {
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
        assertEquals(userResponseDto.getId(), user.getId());
        assertEquals(userResponseDto.getEmail(), user.getEmail());
        assertEquals(userResponseDto.getNickName(), user.getNickName());
        assertEquals(userResponseDto.getRole(), user.getRole().getRole());
    }
}