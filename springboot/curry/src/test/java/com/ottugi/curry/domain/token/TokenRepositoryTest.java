package com.ottugi.curry.domain.token;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TokenRepositoryTest {
    private Token token;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    public void setUp() {
        token = TestObjectFactory.initToken();
        token = tokenRepository.save(token);
    }

    @AfterEach
    public void clean() {
        tokenRepository.deleteAll();
    }

    @Test
    @DisplayName("키인 이메일로 토큰 조회 테스트")
    void testFindByKey() {
        Optional<Token> foundToken = tokenRepository.findByKey(token.getKey());

        assertEquals(token.getKey(), foundToken.get().getKey());
        assertEquals(token.getValue(), foundToken.get().getValue());
        assertEquals(token.getExpiredTime(), foundToken.get().getExpiredTime());
    }
}
