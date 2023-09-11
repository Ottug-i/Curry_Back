package com.ottugi.curry.web.dto.recipe;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeRequestDtoTest {

    private String ingredients1 = "고구마";
    private String ingredients2 = "올리고당";

    private List<String> ingredients = Arrays.asList(ingredients1, ingredients2);

    @Test
    void 레시피_요청_Dto_롬복() {
        // when
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto(USER_ID, ingredients, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition(), PAGE, SIZE);

        // then
        assertEquals(USER_ID, recipeRequestDto.getUserId());
        assertEquals(2, recipeRequestDto.getIngredients().size());
        assertEquals(ingredients1, recipeRequestDto.getIngredients().get(0));
        assertEquals(ingredients2, recipeRequestDto.getIngredients().get(1));
        assertEquals(TIME.getTimeName(), recipeRequestDto.getTime());
        assertEquals(DIFFICULTY.getDifficulty(), recipeRequestDto.getDifficulty());
        assertEquals(COMPOSITION.getComposition(), recipeRequestDto.getComposition());
        assertEquals(PAGE, recipeRequestDto.getPage());
        assertEquals(SIZE, recipeRequestDto.getSize());
    }
}