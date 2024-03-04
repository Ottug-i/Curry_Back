package com.ottugi.curry.web.dto.recipe;

import static com.ottugi.curry.TestConstants.IS_BOOKMARK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeResponseDtoTest {
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = TestObjectFactory.initRecipe();
    }

    @Test
    @DisplayName("RecipeResponseDto 생성 테스트")
    void testRecipeResponseDto() {
        RecipeResponseDto recipeResponseDto = new RecipeResponseDto(recipe, IS_BOOKMARK);

        assertEquals(recipe.getRecipeId(), recipeResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recipeResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recipeResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeResponseDto.getIngredients());
        assertEquals(recipe.getServings().getServings(), recipeResponseDto.getServings());
        assertEquals(recipe.getOrders(), recipeResponseDto.getOrders());
        assertEquals(recipe.getPhoto(), recipeResponseDto.getPhoto());
        assertEquals(IS_BOOKMARK, recipeResponseDto.getIsBookmark());
    }
}