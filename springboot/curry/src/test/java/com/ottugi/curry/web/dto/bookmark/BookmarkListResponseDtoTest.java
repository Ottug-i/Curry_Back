package com.ottugi.curry.web.dto.bookmark;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkTest;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class BookmarkListResponseDtoTest {
    public static BookmarkListResponseDto initBookmarkListResponseDto(Bookmark bookmark) {
        return new BookmarkListResponseDto(bookmark);
    }

    public static List<BookmarkListResponseDto> initBookmarkListResponseDtoList(Bookmark bookmark) {
        return Collections.singletonList(initBookmarkListResponseDto(bookmark));
    }

    public static Page<BookmarkListResponseDto> initBookmarkListResponseDtoPage(Bookmark bookmark) {
        return new PageImpl<>(initBookmarkListResponseDtoList(bookmark));
    }

    private Bookmark bookmark;

    @BeforeEach
    public void setUp() {
        bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(UserTest.initUser());
        bookmark.setRecipe(RecipeTest.initRecipe());
    }

    @Test
    @DisplayName("BookmarkListResponseDto 생성 테스트")
    void testBookmarkListResponseDto() {
        BookmarkListResponseDto bookmarkListResponseDto = initBookmarkListResponseDto(bookmark);

        assertEquals(bookmark.getRecipeId().getRecipeId(), bookmarkListResponseDto.getRecipeId());
        assertEquals(bookmark.getRecipeId().getName(), bookmarkListResponseDto.getName());
        assertEquals(bookmark.getRecipeId().getThumbnail(), bookmarkListResponseDto.getThumbnail());
        assertEquals(bookmark.getRecipeId().getTime().getTimeName(), bookmarkListResponseDto.getTime());
        assertEquals(bookmark.getRecipeId().getDifficulty().getDifficultyName(), bookmarkListResponseDto.getDifficulty());
        assertEquals(bookmark.getRecipeId().getComposition().getCompositionName(), bookmarkListResponseDto.getComposition());
        assertEquals(bookmark.getRecipeId().getIngredients(), bookmarkListResponseDto.getIngredients());
        assertEquals(IS_BOOKMARK, bookmarkListResponseDto.getIsBookmark());
    }
}