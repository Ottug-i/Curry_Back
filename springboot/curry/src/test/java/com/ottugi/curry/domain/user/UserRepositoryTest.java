package com.ottugi.curry.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private User user;
    
    private UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        user = userRepository.save(user);
    }

    @Test
    void 이메일로회원조회() {
        // when
        user = userRepository.findByEmail(EMAIL).get();

        // then
        assertEquals(user.getEmail(), EMAIL);
    }

    @Test
    void 이메일로회원존재조회() {
        // when
        Boolean existUser = userRepository.existsByEmail(EMAIL);

        // then
        assertNotNull(existUser);
    }

    @Test
    void 이메일로회원수조회() {
        // when
        int userEmailCount = userRepository.countByEmail(EMAIL);

        // then
        assertEquals(userEmailCount, 1);
    }
}