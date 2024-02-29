package com.ottugi.curry.web.dto.recipe;

import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.SIZE;
import static com.ottugi.curry.TestConstants.TIME;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.web.dto.recommend.RecipeIngRequestDto;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecipeIngRequestDtoTest {

    private final String ingredients1 = "고구마";
    private final String ingredients2 = "올리고당";

    private final List<String> ingredients = Arrays.asList(ingredients1, ingredients2);

    @Test
    void 레시피_요청_Dto_롬복() {
        // when
        RecipeIngRequestDto recipeIngRequestDto = new RecipeIngRequestDto(USER_ID, ingredients, TIME.getTimeName(),
                DIFFICULTY.getDifficulty(), COMPOSITION.getComposition(), PAGE, SIZE);

        // then
        assertEquals(USER_ID, recipeIngRequestDto.getUserId());
        assertEquals(2, recipeIngRequestDto.getIngredients().size());
        assertEquals(ingredients1, recipeIngRequestDto.getIngredients().get(0));
        assertEquals(ingredients2, recipeIngRequestDto.getIngredients().get(1));
        assertEquals(TIME.getTimeName(), recipeIngRequestDto.getTime());
        assertEquals(DIFFICULTY.getDifficulty(), recipeIngRequestDto.getDifficulty());
        assertEquals(COMPOSITION.getComposition(), recipeIngRequestDto.getComposition());
        assertEquals(PAGE, recipeIngRequestDto.getPage());
        assertEquals(SIZE, recipeIngRequestDto.getSize());
    }
}