package com.ottugi.curry.web.dto.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeSaveRequestDtoTest {
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
    }

    @Test
    @DisplayName("RecipeSaveRequestDto 생성 테스트")
    void testRecipeSaveRequestDto() {
        RecipeSaveRequestDto recipeSaveRequestDto = RecipeSaveRequestDto.builder()
                .recipeId(recipe.getRecipeId())
                .name(recipe.getName())
                .composition(recipe.getComposition().getComposition())
                .ingredients(recipe.getIngredients())
                .servings(recipe.getServings().getServings())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .thumbnail(recipe.getThumbnail())
                .time(recipe.getTime().getTimeName())
                .orders(recipe.getOrders())
                .photo(recipe.getPhoto())
                .genre(recipe.getGenre())
                .build();

        assertEquals(recipe.getRecipeId(), recipeSaveRequestDto.getRecipeId());
        assertEquals(recipe.getName(), recipeSaveRequestDto.getName());
        assertEquals(recipe.getComposition().getComposition(), recipeSaveRequestDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeSaveRequestDto.getIngredients());
        assertEquals(recipe.getServings().getServings(), recipeSaveRequestDto.getServings());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeSaveRequestDto.getDifficulty());
        assertEquals(recipe.getThumbnail(), recipeSaveRequestDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeSaveRequestDto.getTime());
        assertEquals(recipe.getOrders(), recipeSaveRequestDto.getOrders());
        assertEquals(recipe.getPhoto(), recipeSaveRequestDto.getPhoto());
        assertEquals(recipe.getGenre(), recipeSaveRequestDto.getGenre());
    }
}