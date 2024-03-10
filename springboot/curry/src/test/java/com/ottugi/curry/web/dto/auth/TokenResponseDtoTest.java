package com.ottugi.curry.web.dto.auth;

import static com.ottugi.curry.domain.token.TokenTest.VALUE;
import static com.ottugi.curry.domain.user.UserTest.IS_NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenResponseDtoTest {
    public static TokenResponseDto initTokenResponseDto(User user) {
        return new TokenResponseDto(user, VALUE);
    }

    private User user;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("TokenResponseDto 생성 테스트")
    void testTokenResponseDto() {
        TokenResponseDto tokenResponseDto = initTokenResponseDto(user);

        assertEquals(user.getId(), tokenResponseDto.getId());
        assertEquals(user.getEmail(), tokenResponseDto.getEmail());
        assertEquals(user.getNickName(), tokenResponseDto.getNickName());
        assertEquals(user.getRole().getRoleName(), tokenResponseDto.getRole());
        assertEquals(VALUE, tokenResponseDto.getToken());
        assertEquals(IS_NEW, tokenResponseDto.getIsNew());
    }
}