package com.ottugi.curry.web.dto.recipe;

import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeSaveRequestDtoTest {

    @Test
    void 레시피_저장_요청_Dto_롬복() {
        // when
        RecipeSaveRequestDto recipeSaveRequestDto = new RecipeSaveRequestDto(RECIPE_ID, NAME, COMPOSITION.getComposition(), INGREDIENTS, SERVINGS.getServings(), DIFFICULTY.getDifficulty(), THUMBNAIL, TIME.getTimeName(), ORDERS, PHOTO, GENRE);

        // then
        assertEquals(RECIPE_ID, recipeSaveRequestDto.getRecipeId());
        assertEquals(NAME, recipeSaveRequestDto.getName());
        assertEquals(COMPOSITION.getComposition(), recipeSaveRequestDto.getComposition());
        assertEquals(INGREDIENTS, recipeSaveRequestDto.getIngredients());
        assertEquals(SERVINGS.getServings(), recipeSaveRequestDto.getServings());
        assertEquals(DIFFICULTY.getDifficulty(), recipeSaveRequestDto.getDifficulty());
        assertEquals(THUMBNAIL, recipeSaveRequestDto.getThumbnail());
        assertEquals(TIME.getTimeName(), recipeSaveRequestDto.getTime());
        assertEquals(ORDERS, recipeSaveRequestDto.getOrders());
        assertEquals(PHOTO, recipeSaveRequestDto.getPhoto());
        assertEquals(GENRE, recipeSaveRequestDto.getGenre());
    }
}