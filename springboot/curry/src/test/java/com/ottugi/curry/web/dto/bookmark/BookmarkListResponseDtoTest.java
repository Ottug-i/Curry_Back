package com.ottugi.curry.web.dto.bookmark;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class BookmarkListResponseDtoTest {

    private Recipe recipe;
    private Boolean isBookmark = true;

    @Test
    void BookmarkListResponseDto_롬복() {
        // given
        recipe = Recipe.builder()
                .recipeId(RECIPE_ID)
                .name(NAME)
                .thumbnail(THUMBNAIL)
                .time(TIME)
                .difficulty(DIFFICULTY)
                .composition(COMPOSITION)
                .ingredients(INGREDIENTS)
                .servings(SERVINGS)
                .orders(ORDERS)
                .photo(PHOTO)
                .genre(GENRE)
                .build();

        // when
        BookmarkListResponseDto bookmarkListResponseDto = new BookmarkListResponseDto(recipe, isBookmark);

        // then
        assertEquals(bookmarkListResponseDto.getRecipeId(), RECIPE_ID);
        assertEquals(bookmarkListResponseDto.getName(), NAME);
        assertEquals(bookmarkListResponseDto.getThumbnail(), THUMBNAIL);
        assertEquals(bookmarkListResponseDto.getTime(), TIME);
        assertEquals(bookmarkListResponseDto.getDifficulty(), DIFFICULTY);
        assertEquals(bookmarkListResponseDto.getComposition(), COMPOSITION);
        assertEquals(bookmarkListResponseDto.getIngredients(), INGREDIENTS);
        assertEquals(bookmarkListResponseDto.getIsBookmark(), isBookmark);
    }
}