package com.ottugi.curry.domain.token;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TokenRepositoryTest {

    private Token token;
    private Token findToken;

    private TokenRepository tokenRepository;

    @Autowired
    public TokenRepositoryTest(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        token = new Token(EMAIL, VALUE, EXPIRED_TIME);
        token = tokenRepository.save(token);
    }

    @AfterEach
    public void clean() {
        // clean
        tokenRepository.deleteAll();
    }

    @Test
    void 키로조회() {
        // when
        findToken = tokenRepository.findByKey(token.getKey()).get();

        // then
        assertEquals(findToken.getKey(), EMAIL);
        assertEquals(findToken.getValue(), VALUE);
    }
}
