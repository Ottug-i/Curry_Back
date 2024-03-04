package com.ottugi.curry.web.dto.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.bookmark.Bookmark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookmarkRequestDtoTest {
    private Bookmark bookmark;

    @BeforeEach
    public void setUp() {
        bookmark = TestObjectFactory.initBookmark();
        bookmark.setUser(TestObjectFactory.initUser());
        bookmark.setRecipe(TestObjectFactory.initRecipe());
    }

    @Test
    @DisplayName("BookmarkRequestDto 생성 테스트")
    void testBookmarkRequestDto() {
        BookmarkRequestDto bookmarkRequestDto = BookmarkRequestDto.builder()
                .userId(bookmark.getUserId().getId())
                .recipeId(bookmark.getRecipeId().getRecipeId())
                .build();

        assertEquals(bookmark.getUserId().getId(), bookmarkRequestDto.getUserId());
        assertEquals(bookmark.getRecipeId().getRecipeId(), bookmarkRequestDto.getRecipeId());
    }
}