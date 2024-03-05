package com.ottugi.curry.domain.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookmarkTest {
    public static final Long BOOKMARK_ID = 1L;
    public static final Boolean IS_BOOKMARK = Boolean.TRUE;

    public static Bookmark initBookmark() {
        return Bookmark.builder()
                .id(BOOKMARK_ID)
                .build();
    }

    private Bookmark bookmark;

    @BeforeEach
    public void setUp() {
        bookmark = initBookmark();
    }

    @Test
    @DisplayName("북마크 추가 테스트")
    void testBookmark() {
        assertNotNull(bookmark);
        assertEquals(BOOKMARK_ID, bookmark.getId());
    }

    @Test
    @DisplayName("북마크의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        bookmark.setUser(user);

        assertEquals(user, bookmark.getUserId());
        verify(user, times(2)).getBookmarkList();
    }

    @Test
    @DisplayName("북마크의 레시피 연관관계 설정 테스트")
    void testSetRecipe() {
        Recipe recipe = mock(Recipe.class);
        bookmark.setRecipe(recipe);

        assertEquals(recipe, bookmark.getRecipeId());
    }
}