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
    void RecipeRequestDto_롬복() {
        // when
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto(USER_ID, ingredients, PAGE, SIZE);

        // then
        assertEquals(USER_ID, recipeRequestDto.getUserId());
        assertEquals(2, recipeRequestDto.getIngredients().size());
        assertEquals(ingredients1, recipeRequestDto.getIngredients().get(0));
        assertEquals(ingredients2, recipeRequestDto.getIngredients().get(1));
        assertEquals(PAGE, recipeRequestDto.getPage());
        assertEquals(SIZE, recipeRequestDto.getSize());
    }
}