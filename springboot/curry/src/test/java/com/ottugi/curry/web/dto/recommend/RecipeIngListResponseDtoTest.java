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
    void 재료로_레시피_목록_응답_Dto_롬복() {
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
        assertEquals(recipe.getRecipeId(), recipeIngListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recipeIngListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recipeIngListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recipeIngListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeIngListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeIngListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recipeIngListResponseDto.getIngredients());
        assertEquals(isBookmark, recipeIngListResponseDto.getIsBookmark());
    }
}