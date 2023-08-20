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
        RecipeIngListResponseDto recipeIngListResponseDto = new RecipeIngListResponseDto(ingredients, recipe, isBookmark);

        // then
        assertEquals(recipeIngListResponseDto.getRecipeId(), recipe.getRecipeId());
        assertEquals(recipeIngListResponseDto.getName(), recipe.getName());
        assertEquals(recipeIngListResponseDto.getThumbnail(), recipe.getThumbnail());
        assertEquals(recipeIngListResponseDto.getTime(), recipe.getTime().getTimeName());
        assertEquals(recipeIngListResponseDto.getDifficulty(), recipe.getDifficulty().getDifficulty());
        assertEquals(recipeIngListResponseDto.getComposition(), recipe.getComposition().getComposition());
        assertEquals(recipeIngListResponseDto.getIngredients(), recipe.getIngredients());
        assertEquals(recipeIngListResponseDto.getIsBookmark(), isBookmark);
    }
}