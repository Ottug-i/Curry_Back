package com.ottugi.curry.web.dto.auth;

import static com.ottugi.curry.TestConstants.IS_NEW;
import static com.ottugi.curry.TestConstants.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenResponseDtoTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
    }

    @Test
    @DisplayName("TokenResponseDto 생성 테스트")
    void testTokenResponseDto() {
        TokenResponseDto tokenResponseDto = new TokenResponseDto(user, VALUE);

        assertEquals(user.getId(), tokenResponseDto.getId());
        assertEquals(user.getEmail(), tokenResponseDto.getEmail());
        assertEquals(user.getNickName(), tokenResponseDto.getNickName());
        assertEquals(user.getRole().getRole(), tokenResponseDto.getRole());
        assertEquals(VALUE, tokenResponseDto.getToken());
        assertEquals(IS_NEW, tokenResponseDto.getIsNew());
    }
}