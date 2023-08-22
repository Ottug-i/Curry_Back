package com.ottugi.curry.web.dto.bookmark;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class BookmarkListResponseDtoTest {

    private Recipe recipe;
    private Boolean isBookmark = true;

    @Test
    void 북마크_목록_응답_Dto_롬복() {
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
        assertEquals(recipe.getRecipeId(), bookmarkListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), bookmarkListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), bookmarkListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), bookmarkListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), bookmarkListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), bookmarkListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), bookmarkListResponseDto.getIngredients());
        assertEquals(isBookmark, bookmarkListResponseDto.getIsBookmark());
    }
}