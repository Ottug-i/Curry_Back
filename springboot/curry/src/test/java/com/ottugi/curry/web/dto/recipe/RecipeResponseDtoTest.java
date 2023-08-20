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
        assertEquals(recipeResponseDto.getRecipeId(), recipe.getRecipeId());
        assertEquals(recipeResponseDto.getName(), recipe.getName());
        assertEquals(recipeResponseDto.getThumbnail(), recipe.getThumbnail());
        assertEquals(recipeResponseDto.getTime(), recipe.getTime().getTimeName());
        assertEquals(recipeResponseDto.getDifficulty(), recipe.getDifficulty().getDifficulty());
        assertEquals(recipeResponseDto.getComposition(), recipe.getComposition().getComposition());
        assertEquals(recipeResponseDto.getIngredients(), recipe.getIngredients());
        assertEquals(recipeResponseDto.getServings(), recipe.getServings().getServings());
        assertEquals(recipeResponseDto.getOrders(), recipe.getOrders());
        assertEquals(recipeResponseDto.getPhoto(), recipe.getPhoto());
        assertEquals(recipeResponseDto.getIsBookmark(), isBookmark);
    }
}