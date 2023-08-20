package com.ottugi.curry.web.dto.recipe;

import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeSaveRequestDtoTest {

    @Test
    void RecipeSaveRequestDto_롬복() {
        // when
        RecipeSaveRequestDto recipeSaveRequestDto = new RecipeSaveRequestDto(RECIPE_ID, NAME, COMPOSITION.getComposition(), INGREDIENTS, SERVINGS.getServings(), DIFFICULTY.getDifficulty(), THUMBNAIL, TIME.getTimeName(), ORDERS, PHOTO, GENRE);

        // then
        assertEquals(recipeSaveRequestDto.getRecipeId(), RECIPE_ID);
        assertEquals(recipeSaveRequestDto.getName(), NAME);
        assertEquals(recipeSaveRequestDto.getComposition(), COMPOSITION.getComposition());
        assertEquals(recipeSaveRequestDto.getIngredients(), INGREDIENTS);
        assertEquals(recipeSaveRequestDto.getServings(), SERVINGS.getServings());
        assertEquals(recipeSaveRequestDto.getDifficulty(), DIFFICULTY.getDifficulty());
        assertEquals(recipeSaveRequestDto.getThumbnail(), THUMBNAIL);
        assertEquals(recipeSaveRequestDto.getTime(), TIME.getTimeName());
        assertEquals(recipeSaveRequestDto.getOrders(), ORDERS);
        assertEquals(recipeSaveRequestDto.getPhoto(), PHOTO);
        assertEquals(recipeSaveRequestDto.getGenre(), GENRE);
    }
}