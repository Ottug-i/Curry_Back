package com.ottugi.curry.domain.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TokenRepositoryTest {

    private Token token;

    private TokenRepository tokenRepository;

    @Autowired
    public TokenRepositoryTest(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        token = new Token(EMAIL, VALUE, EXPIRED_TIME);
        tokenRepository.save(token);
    }

    @Test
    void 키로조회() {
        // when
        token = tokenRepository.findByKey(EMAIL).get();

        // then
        assertEquals(token.getKey(), EMAIL);
        assertEquals(token.getValue(), VALUE);
    }
}
