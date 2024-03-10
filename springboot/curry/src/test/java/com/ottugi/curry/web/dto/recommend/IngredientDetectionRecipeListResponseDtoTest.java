package com.ottugi.curry.web.dto.recommend;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static com.ottugi.curry.domain.recipe.RecipeTest.INGREDIENT;
import static com.ottugi.curry.domain.recipe.RecipeTest.IS_FAVORITE_GENRE;
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

public class IngredientDetectionRecipeListResponseDtoTest {
    private static IngredientDetectionRecipeListResponseDto initIngredientDetectionRecipeListResponseDto(Recipe recipe) {
        return new IngredientDetectionRecipeListResponseDto(List.of(INGREDIENT), recipe, IS_FAVORITE_GENRE, IS_BOOKMARK);
    }

    public static List<IngredientDetectionRecipeListResponseDto> initIngredientDetectionRecipeListResponseDtoList(Recipe recipe) {
        return Collections.singletonList(initIngredientDetectionRecipeListResponseDto(recipe));
    }

    public static Page<IngredientDetectionRecipeListResponseDto> initIngredientDetectionRecipeListResponseDtoPage(Recipe recipe) {
        return new PageImpl<>(initIngredientDetectionRecipeListResponseDtoList(recipe));
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("IngredientDetectionRecipeListResponseDto 생성 테스트")
    void testIngredientDetectionRecipeListResponseDto() {
        IngredientDetectionRecipeListResponseDto ingredientDetectionRecipeListResponseDto = initIngredientDetectionRecipeListResponseDto(recipe);

        assertEquals(recipe.getRecipeId(), ingredientDetectionRecipeListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), ingredientDetectionRecipeListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), ingredientDetectionRecipeListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), ingredientDetectionRecipeListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficultyName(), ingredientDetectionRecipeListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), ingredientDetectionRecipeListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), ingredientDetectionRecipeListResponseDto.getIngredients());
        assertEquals(IS_FAVORITE_GENRE, ingredientDetectionRecipeListResponseDto.getIsFavoriteGenre());
        assertEquals(IS_BOOKMARK, ingredientDetectionRecipeListResponseDto.getIsBookmark());
    }
}