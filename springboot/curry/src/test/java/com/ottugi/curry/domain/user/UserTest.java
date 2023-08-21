package com.ottugi.curry.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
    }

    @Test
    void 회원추가() {
        // when, then
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NICKNAME, user.getNickName());
        assertEquals(FAVORITE_GENRE, user.getFavoriteGenre());
        assertEquals(ROLE, user.getRole());
    }

    @Test
    void 회원닉네임수정() {
        // when
        user.updateProfile(NEW_NICKNAME);

        // then
        assertEquals(NEW_NICKNAME, user.getNickName());
    }

    @Test
    void 회원장르수정() {
        // when
        user.updateGenre(NEW_FAVORITE_GENRE);

        // then
        assertEquals(NEW_FAVORITE_GENRE, user.getFavoriteGenre());
    }

    @Test
    void 회원역할수정() {
        // when
        user.updateRole(NEW_ROLE);

        // then
        assertEquals(NEW_ROLE, user.getRole());
    }
}