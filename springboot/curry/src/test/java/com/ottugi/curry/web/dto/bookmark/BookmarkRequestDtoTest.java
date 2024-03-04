package com.ottugi.curry.web.dto.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkTest;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookmarkRequestDtoTest {
    public static BookmarkRequestDto initBookmarkRequestDto(Bookmark bookmark) {
        return BookmarkRequestDto.builder()
                .userId(bookmark.getUserId().getId())
                .recipeId(bookmark.getRecipeId().getRecipeId())
                .build();
    }

    private Bookmark bookmark;

    @BeforeEach
    public void setUp() {
        bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(UserTest.initUser());
        bookmark.setRecipe(RecipeTest.initRecipe());
    }

    @Test
    @DisplayName("BookmarkRequestDto 생성 테스트")
    void testBookmarkRequestDto() {
        BookmarkRequestDto bookmarkRequestDto = initBookmarkRequestDto(bookmark);

        assertEquals(bookmark.getUserId().getId(), bookmarkRequestDto.getUserId());
        assertEquals(bookmark.getRecipeId().getRecipeId(), bookmarkRequestDto.getRecipeId());
    }
}