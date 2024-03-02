package com.ottugi.curry.domain.user;

import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.NEW_FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.NEW_NICKNAME;
import static com.ottugi.curry.TestConstants.NEW_ROLE;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ROLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.lately.Lately;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
    }

    @Test
    @DisplayName("회원 추가 테스트")
    void testUser() {
        assertNotNull(user);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NICKNAME, user.getNickName());
        assertEquals(FAVORITE_GENRE, user.getFavoriteGenre());
        assertEquals(true, user.getIsNew());
        assertEquals(ROLE, user.getRole());
    }

    @Test
    @DisplayName("회원 닉네임 수정 테스트")
    void testUpdateProfile() {
        user.updateProfile(NEW_NICKNAME);

        assertEquals(NEW_NICKNAME, user.getNickName());
    }

    @Test
    @DisplayName("회원이 좋아하는 레시피 장르 수정 테스트")
    void testUpdateGenre() {
        user.updateGenre(NEW_FAVORITE_GENRE);

        assertEquals(NEW_FAVORITE_GENRE, user.getFavoriteGenre());
    }

    @Test
    @DisplayName("회원 권한 수정 테스트")
    void testUpdateRole() {
        user.updateRole(NEW_ROLE);

        assertEquals(NEW_ROLE, user.getRole());
    }

    @Test
    @DisplayName("신규 회원에서 기존 회원으로 변경 테스트")
    void testMarkAsExistingUser() {
        user.markAsExistingUser();

        assertEquals(false, user.getIsNew());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void testWithdrawUser() {
        user.withdrawUser();

        assertEquals(true, user.getWithdraw());
        assertEquals("탈퇴한 회원", user.getNickName());
    }

    @Test
    @DisplayName("회원의 북마크 연관관계 설정 테스트")
    void testAddBookmarkList() {
        Bookmark bookmark = mock(Bookmark.class);
        user.addBookmarkList(bookmark);

        assertEquals(bookmark, user.getBookmarkList().get(0));
        verify(bookmark, times(1)).setUser(user);
    }

    @Test
    @DisplayName("회원의 최근 본 레시피 연관관계 설정 테스트")
    void testAddLatelyList() {
        Lately lately = mock(Lately.class);
        user.addLatelyList(lately);

        assertEquals(lately, user.getLatelyList().get(0));
        verify(lately, times(1)).setUser(user);
    }
}