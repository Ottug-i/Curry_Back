package com.ottugi.curry.domain.token;

import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.EXPIRED_TIME;
import static com.ottugi.curry.TestConstants.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenTest {
    private Token token;

    @BeforeEach
    public void setUp() {
        token = TestObjectFactory.initToken();
    }

    @Test
    @DisplayName("토큰 추가 테스트")
    void testToken() {
        assertNotNull(token);
        assertEquals(EMAIL, token.getKey());
        assertEquals(VALUE, token.getValue());
        assertEquals(EXPIRED_TIME, token.getExpiredTime());
    }
}
