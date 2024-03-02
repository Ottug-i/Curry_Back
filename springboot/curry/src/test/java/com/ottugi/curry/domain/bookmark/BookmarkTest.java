package com.ottugi.curry.domain.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookmarkTest {
    private Bookmark bookmark;

    @BeforeEach
    public void setUp() {
        bookmark = TestObjectFactory.initBookmark();
    }

    @Test
    @DisplayName("북마크 추가 테스트")
    void testBookmark() {
        assertNotNull(bookmark);
    }

    @Test
    @DisplayName("북마크의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        bookmark.setUser(user);

        assertEquals(bookmark.getUserId(), user);
        verify(user, times(1)).getBookmarkList();
    }

    @Test
    @DisplayName("북마크의 레시피 연관관계 설정 테스트")
    void testSetRecipe() {
        Recipe recipe = mock(Recipe.class);
        bookmark.setRecipe(recipe);

        assertEquals(bookmark.getRecipeId(), recipe);
    }
}