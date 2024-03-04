package com.ottugi.curry.web.dto.recipe;

import static com.ottugi.curry.TestConstants.IS_BOOKMARK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeListResponseDtoTest {
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = TestObjectFactory.initRecipe();
    }

    @Test
    @DisplayName("RecipeListResponseDto 생성 테스트")
    void testRecipeListResponseDto() {
        RecipeListResponseDto recipeListResponseDto = new RecipeListResponseDto(recipe, IS_BOOKMARK);
        
        assertEquals(recipe.getRecipeId(), recipeListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recipeListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recipeListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeListResponseDto.getIngredients());
        assertEquals("vegetable", recipeListResponseDto.getMainGenre());
        assertEquals(IS_BOOKMARK, recipeListResponseDto.getIsBookmark());
    }
}