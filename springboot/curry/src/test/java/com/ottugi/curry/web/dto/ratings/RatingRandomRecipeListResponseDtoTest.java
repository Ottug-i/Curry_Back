package com.ottugi.curry.web.dto.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RatingRandomRecipeListResponseDtoTest {
    private static RatingRandomRecipeListResponseDto initRatingRandomRecipeListResponseDto(Recipe recipe) {
        return new RatingRandomRecipeListResponseDto(recipe);
    }

    public static List<RatingRandomRecipeListResponseDto> initRatingRandomRecipeListResponseDtoList(Recipe recipe) {
        return Collections.singletonList(initRatingRandomRecipeListResponseDto(recipe));
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("RatingRandomRecipeListResponseDto 생성 테스트")
    void testRatingRandomRecipeListResponseDto() {
        RatingRandomRecipeListResponseDto recommendListResponseDto = new RatingRandomRecipeListResponseDto(recipe);

        assertEquals(recipe.getRecipeId(), recommendListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recommendListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recommendListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recommendListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficultyName(), recommendListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), recommendListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recommendListResponseDto.getIngredients());
    }
}