package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeResponseDtoTest {

    private Boolean isBookmark = true;

    @Test
    void RecipeResponseDto_롬복() {
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
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto(recipe, isBookmark);

        // then
        assertEquals(recipe.getRecipeId(), recipeResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recipeResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recipeResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeResponseDto.getIngredients());
        assertEquals(recipe.getServings().getServings(), recipeResponseDto.getServings());
        assertEquals(recipe.getOrders(), recipeResponseDto.getOrders());
        assertEquals(recipe.getPhoto(), recipeResponseDto.getPhoto());
        assertEquals(isBookmark, recipeResponseDto.getIsBookmark());
    }
}