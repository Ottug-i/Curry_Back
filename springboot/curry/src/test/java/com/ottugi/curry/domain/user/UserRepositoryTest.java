package com.ottugi.curry.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    // 이미 DB에 저장되어있는 데이터 사용
    private final String email = "wn8925@sookmyung.ac.kr";

    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로회원조회() {

        // when
        User findUser = userRepository.findByEmail(email);

        // then
        assertEquals(findUser.getEmail(), email);
    }

    @Test
    void 이메일로회원수조회() {

        // when
        int userEmailCount = userRepository.countByEmail(email);

        // then
        assertEquals(userEmailCount, 1);
    }
}