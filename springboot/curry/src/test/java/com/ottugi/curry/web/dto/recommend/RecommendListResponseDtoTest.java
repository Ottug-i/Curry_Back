package com.ottugi.curry.web.dto.recommend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecommendListResponseDtoTest {
    public static RecommendListResponseDto initRecommendListResponseDto(Recipe recipe) {
        return new RecommendListResponseDto(recipe);
    }

    public static List<RecommendListResponseDto> initRecommendListResponseDtoList(Recipe recipe) {
        return Collections.singletonList(initRecommendListResponseDto(recipe));
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("RecommendListResponseDto 생성 테스트")
    void testRecommendListResponseDto() {
        RecommendListResponseDto recommendListResponseDto = new RecommendListResponseDto(recipe);

        assertEquals(recipe.getRecipeId(), recommendListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recommendListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recommendListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recommendListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recommendListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recommendListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recommendListResponseDto.getIngredients());
    }
}