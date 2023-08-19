package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeListResponseDtoTest {

    private Boolean isBookmark = true;

    @Test
    void RecipeListResponseDto_롬복() {
        // given
        Recipe recipe = Recipe.builder()
                .recipeId(NEW_RECIPE_ID)
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
        RecipeListResponseDto recipeListResponseDto = new RecipeListResponseDto(recipe, isBookmark);

        // then
        assertEquals(recipeListResponseDto.getRecipeId(), NEW_RECIPE_ID);
        assertEquals(recipeListResponseDto.getName(), NAME);
        assertEquals(recipeListResponseDto.getThumbnail(), THUMBNAIL);
        assertEquals(recipeListResponseDto.getTime(), "60분 이내");
        assertEquals(recipeListResponseDto.getDifficulty(), "초급");
        assertEquals(recipeListResponseDto.getComposition(), "가볍게");
        assertEquals(recipeListResponseDto.getIngredients(), INGREDIENTS);
        assertEquals(recipeListResponseDto.getIsBookmark(), isBookmark);
    }
}