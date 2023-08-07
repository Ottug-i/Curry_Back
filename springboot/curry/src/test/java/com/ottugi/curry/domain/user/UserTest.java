package com.ottugi.curry.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    private final String email = "wn8925@gmail.com";
    private final String nickName = "가경";
    private final String newNickName = "가경이";

    private User user;

    @BeforeEach
    public void setUp() {

        // given
        user = User.builder().email(email).nickName(nickName).build();
    }

    @Test
    void 회원추가() {

        // when, then
        assertEquals(user.getEmail(), email);
        assertEquals(user.getNickName(), nickName);
    }

    @Test
    void 회원수정() {

        // when
        user.updateProfile(newNickName);

        // then
        assertEquals(user.getNickName(), newNickName);
    }
}