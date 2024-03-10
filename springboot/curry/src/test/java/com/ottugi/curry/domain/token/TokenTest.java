package com.ottugi.curry.domain.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenTest {
    public static final String VALUE = "token";
    public static final Long EXPIRED_TIME = Duration.ofDays(14).toMillis();

    public static Token initToken(User user) {
        return Token.builder()
                .key(user.getEmail())
                .value(VALUE)
                .expiredTime(EXPIRED_TIME)
                .build();
    }

    private User user;
    private Token token;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
        token = initToken(user);
    }

    @Test
    @DisplayName("토큰 추가 테스트")
    void testToken() {
        assertNotNull(token);
        assertEquals(user.getEmail(), token.getKey());
        assertEquals(VALUE, token.getValue());
        assertEquals(EXPIRED_TIME, token.getExpiredTime());
    }
}
