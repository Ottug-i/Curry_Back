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
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto(recipe, isBookmark);

        // then
        assertEquals(recipeResponseDto.getRecipeId(), NEW_RECIPE_ID);
        assertEquals(recipeResponseDto.getName(), NAME);
        assertEquals(recipeResponseDto.getThumbnail(), THUMBNAIL);
        assertEquals(recipeResponseDto.getTime(), TIME);
        assertEquals(recipeResponseDto.getDifficulty(), DIFFICULTY);
        assertEquals(recipeResponseDto.getComposition(), COMPOSITION);
        assertEquals(recipeResponseDto.getIngredients(), INGREDIENTS);
        assertEquals(recipeResponseDto.getServings(), SERVINGS);
        assertEquals(recipeResponseDto.getOrders(), ORDERS);
        assertEquals(recipeResponseDto.getPhoto(), PHOTO);
        assertEquals(recipeResponseDto.getIsBookmark(), isBookmark);
    }
}