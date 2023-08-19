package com.ottugi.curry.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private String email = "wn8925@sookmyung.ac.kr";
    
    private User user;
    
    private UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void 이메일로회원조회() {
        // when
        user = userRepository.findByEmail(email).get();

        // then
        assertEquals(user.getEmail(), email);
    }

    @Test
    void 이메일로회원존재조회() {
        // when
        Boolean existUser = userRepository.existsByEmail(email);

        // then
        assertNotNull(existUser);
    }

    @Test
    void 이메일로회원수조회() {
        // when
        int userEmailCount = userRepository.countByEmail(email);

        // then
        assertEquals(userEmailCount, 1);
    }
}