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
        RecipeListResponseDto recipeListResponseDto = new RecipeListResponseDto(recipe, isBookmark);

        // then
        assertEquals(recipeListResponseDto.getRecipeId(), recipe.getRecipeId());
        assertEquals(recipeListResponseDto.getName(), recipe.getName());
        assertEquals(recipeListResponseDto.getThumbnail(), recipe.getThumbnail());
        assertEquals(recipeListResponseDto.getTime(), recipe.getTime().getTimeName());
        assertEquals(recipeListResponseDto.getDifficulty(), recipe.getDifficulty().getDifficulty());
        assertEquals(recipeListResponseDto.getComposition(), recipe.getComposition().getComposition());
        assertEquals(recipeListResponseDto.getIngredients(), recipe.getIngredients());
        assertEquals(recipeListResponseDto.getIsBookmark(), isBookmark);
    }
}