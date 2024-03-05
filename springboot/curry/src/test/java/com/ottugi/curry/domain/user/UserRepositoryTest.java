package com.ottugi.curry.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {
    private User user;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
        user = userRepository.save(user);
    }

    @Test
    @DisplayName("이메일로 회원 조회 테스트")
    void testFindByEmail() {
        Optional<User> foundUserOptional = userRepository.findByEmail(user.getEmail());

        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals(user.getEmail(), foundUser.getEmail());
    }
}