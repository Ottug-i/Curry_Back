package com.ottugi.curry.web.dto.recipe;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeListResponseDtoTest {
    public static RecipeListResponseDto initRecipeListResponseDto(Recipe recipe) {
        return new RecipeListResponseDto(recipe, IS_BOOKMARK);
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("RecipeListResponseDto 생성 테스트")
    void testRecipeListResponseDto() {
        RecipeListResponseDto recipeListResponseDto = initRecipeListResponseDto(recipe);

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