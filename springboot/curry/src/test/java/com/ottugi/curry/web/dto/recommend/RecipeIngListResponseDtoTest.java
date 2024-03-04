package com.ottugi.curry.web.dto.recommend;

import static com.ottugi.curry.domain.bookmark.BookmarkTest.IS_BOOKMARK;
import static com.ottugi.curry.domain.recipe.RecipeTest.INGREDIENT1;
import static com.ottugi.curry.domain.recipe.RecipeTest.INGREDIENT2;
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

public class RecipeIngListResponseDtoTest {
    public static RecipeIngListResponseDto initRecommendRequestDto(Recipe recipe) {
        return new RecipeIngListResponseDto(List.of(INGREDIENT1, INGREDIENT2), recipe, IS_FAVORITE_GENRE, IS_BOOKMARK);
    }

    public static Page<RecipeIngListResponseDto> initRecipeIngListResponseDtoPage(Recipe recipe) {
        return new PageImpl<>(Collections.singletonList(initRecommendRequestDto(recipe)));
    }

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("RecipeIngListResponseDto 생성 테스트")
    void testRecipeIngListResponseDto() {
        RecipeIngListResponseDto recipeIngListResponseDto = initRecommendRequestDto(recipe);

        assertEquals(recipe.getRecipeId(), recipeIngListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recipeIngListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recipeIngListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeIngListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeIngListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeIngListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeIngListResponseDto.getIngredients());
        assertEquals(IS_BOOKMARK, recipeIngListResponseDto.getIsBookmark());
    }
}