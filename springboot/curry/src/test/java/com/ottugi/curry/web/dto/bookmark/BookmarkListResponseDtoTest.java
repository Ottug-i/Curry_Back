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
                .id(ID)
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
        assertEquals(bookmarkListResponseDto.getRecipeId(), recipe.getRecipeId());
        assertEquals(bookmarkListResponseDto.getName(), recipe.getName());
        assertEquals(bookmarkListResponseDto.getThumbnail(), recipe.getThumbnail());
        assertEquals(bookmarkListResponseDto.getTime(), recipe.getTime().getTimeName());
        assertEquals(bookmarkListResponseDto.getDifficulty(), recipe.getDifficulty().getDifficulty());
        assertEquals(bookmarkListResponseDto.getComposition(), recipe.getComposition().getComposition());
        assertEquals(bookmarkListResponseDto.getIngredients(), recipe.getIngredients());
        assertEquals(bookmarkListResponseDto.getIsBookmark(), isBookmark);
    }
}