package com.ottugi.curry.web.dto.recipe;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RecipeRequestDtoTest {

    @Test
    void RecipeRequestDto_롬복() {

        // given
        Long userId = 1L;
        String ingredients1 = "고구마";
        String ingredients2 = "올리고당";
        int page = 1;
        int size = 10;

        // when
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto(userId, Arrays.asList(ingredients1, ingredients2), page, size);

        // then
        assertEquals(recipeRequestDto.getUserId(), userId);
        assertEquals(recipeRequestDto.getIngredients().size(), 2);
        assertEquals(recipeRequestDto.getIngredients().get(0), ingredients1);
        assertEquals(recipeRequestDto.getIngredients().get(1), ingredients2);
        assertEquals(recipeRequestDto.getPage(), page);
        assertEquals(recipeRequestDto.getSize(), size);
    }
}