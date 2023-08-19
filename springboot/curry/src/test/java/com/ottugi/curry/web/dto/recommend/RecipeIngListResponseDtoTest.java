package com.ottugi.curry.web.dto.recommend;

import com.ottugi.curry.domain.recipe.Recipe;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeIngListResponseDtoTest {

    private Recipe recipe;
    private Boolean isBookmark = true;
    private List<String> ingredients = Arrays.asList("고구마");

    @Test
    void RecipeIngListResponseDto_롬복() {
        // given
        recipe = Recipe.builder()
                .recipeId(NEW_RECIPE_ID)
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
        RecipeIngListResponseDto recipeIngListResponseDto = new RecipeIngListResponseDto(ingredients, recipe, isBookmark);

        // then
        assertEquals(recipeIngListResponseDto.getRecipeId(), NEW_RECIPE_ID);
        assertEquals(recipeIngListResponseDto.getName(), NAME);
        assertEquals(recipeIngListResponseDto.getThumbnail(), THUMBNAIL);
        assertEquals(recipeIngListResponseDto.getTime(), TIME);
        assertEquals(recipeIngListResponseDto.getDifficulty(), DIFFICULTY);
        assertEquals(recipeIngListResponseDto.getComposition(), COMPOSITION);
        assertEquals(recipeIngListResponseDto.getIngredients(), INGREDIENTS);
        assertEquals(recipeIngListResponseDto.getIsBookmark(), isBookmark);
    }
}