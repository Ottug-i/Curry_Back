package com.ottugi.curry.domain.user;

import com.ottugi.curry.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class UserTest {

    private User user;

    private final TestConstants testConstants;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
    }

    @Test
    void 회원추가() {
        // when, then
        assertEquals(user.getEmail(), EMAIL);
        assertEquals(user.getNickName(), NICKNAME);
        assertEquals(user.getFavoriteGenre(), FAVORITE_GENRE);
        assertEquals(user.getRole(), ROLE);
    }

    @Test
    void 회원닉네임수정() {
        // when
        user.updateProfile(NEW_NICKNAME);

        // then
        assertEquals(user.getNickName(), NEW_NICKNAME);
    }

    @Test
    void 회원장르수정() {
        // when
        user.updateGenre(NEW_FAVORITE_GENRE);

        // then
        assertEquals(user.getFavoriteGenre(), NEW_FAVORITE_GENRE);
    }

    @Test
    void 회원역할수정() {
        // when
        user.updateRole(NEW_ROLE);

        // then
        assertEquals(user.getRole(), NEW_ROLE);
    }
}