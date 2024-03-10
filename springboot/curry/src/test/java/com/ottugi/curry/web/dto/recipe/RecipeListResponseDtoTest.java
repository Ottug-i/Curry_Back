package com.ottugi.curry.web.dto.recipe;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class RecipeListResponseDtoTest {
    private static RecipeListResponseDto initRecipeListResponseDto(Recipe recipe) {
        return new RecipeListResponseDto(recipe, IS_BOOKMARK);
    }

    public static List<RecipeListResponseDto> initRecipeListResponseDtoList(Recipe recipe) {
        return Collections.singletonList(initRecipeListResponseDto(recipe));
    }

    public static Page<RecipeListResponseDto> initRecipeListResponseDtoPage(Recipe recipe) {
        return new PageImpl<>(initRecipeListResponseDtoList(recipe));
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
        assertEquals(recipe.getDifficulty().getDifficultyName(), recipeListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), recipeListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeListResponseDto.getIngredients());
        assertEquals("vegetable", recipeListResponseDto.getMainGenre());
        assertEquals(IS_BOOKMARK, recipeListResponseDto.getIsBookmark());
    }
}