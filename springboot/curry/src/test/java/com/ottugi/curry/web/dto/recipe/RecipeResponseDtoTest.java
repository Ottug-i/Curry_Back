package com.ottugi.curry.web.dto.recipe;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeResponseDtoTest {
    public static RecipeResponseDto initRecipeResponseDto(Recipe recipe) {
        return new RecipeResponseDto(recipe, IS_BOOKMARK);
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("RecipeResponseDto 생성 테스트")
    void testRecipeResponseDto() {
        RecipeResponseDto recipeResponseDto = initRecipeResponseDto(recipe);

        assertEquals(recipe.getRecipeId(), recipeResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recipeResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recipeResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficultyName(), recipeResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), recipeResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeResponseDto.getIngredients());
        assertEquals(recipe.getServings().getServingName(), recipeResponseDto.getServings());
        assertEquals(recipe.getOrders(), recipeResponseDto.getOrders());
        assertEquals(recipe.getPhoto(), recipeResponseDto.getPhoto());
        assertEquals(IS_BOOKMARK, recipeResponseDto.getIsBookmark());
    }
}