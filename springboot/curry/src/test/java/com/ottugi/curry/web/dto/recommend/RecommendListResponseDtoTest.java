package com.ottugi.curry.web.dto.recommend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecommendListResponseDtoTest {
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = TestObjectFactory.initRecipe();
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