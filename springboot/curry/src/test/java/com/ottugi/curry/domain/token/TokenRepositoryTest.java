package com.ottugi.curry.domain.token;

import com.ottugi.curry.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
public class TokenRepositoryTest {

    private Token token;

    private final TestConstants testConstants;
    private final TokenRepository tokenRepository;

    @BeforeEach
    public void setUp() {
        // given
        token = new Token(EMAIL, VALUE, EXPIRED_TIME);
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
