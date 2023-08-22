package com.ottugi.curry.web.dto.recipe;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeListResponseDtoTest {

    private Boolean isBookmark = true;

    @Test
    void 레시피_목록_응답_Dto_롬복() {
        // given
        Recipe recipe = Recipe.builder()
                .id(ID)
                .recipeId(RECIPE_ID)
                .name(NAME)
                .thumbnail(THUMBNAIL)
                .time(TIME)
                .difficulty(DIFFICULTY)
                .composition(COMPOSITION)
                .ingredients(INGREDIENTS)
                .servings(SERVINGS)
                .orders(ORDERS)
                .photo(PHOTO)
                .genre(GENRE)
                .build();

        // when
        RecipeListResponseDto recipeListResponseDto = new RecipeListResponseDto(recipe, isBookmark);

        // then
        assertEquals(recipe.getRecipeId(), recipeListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recipeListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recipeListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeListResponseDto.getIngredients());
        assertEquals(isBookmark, recipeListResponseDto.getIsBookmark());
    }
}