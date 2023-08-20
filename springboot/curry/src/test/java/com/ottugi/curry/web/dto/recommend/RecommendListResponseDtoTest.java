package com.ottugi.curry.web.dto.recommend;

import com.ottugi.curry.domain.recipe.Recipe;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecommendListResponseDtoTest {

    private Recipe recipe;

    @Test
    void RecommendListResponseDto_롬복() {
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
        RecommendListResponseDto recommendListResponseDto = new RecommendListResponseDto(recipe);

        // then
        assertEquals(recommendListResponseDto.getRecipeId(), recipe.getRecipeId());
        assertEquals(recommendListResponseDto.getName(), recipe.getName());
        assertEquals(recommendListResponseDto.getThumbnail(), recipe.getThumbnail());
        assertEquals(recommendListResponseDto.getTime(), recipe.getTime().getTimeName());
        assertEquals(recommendListResponseDto.getDifficulty(), recipe.getDifficulty().getDifficulty());
        assertEquals(recommendListResponseDto.getComposition(), recipe.getComposition().getComposition());
        assertEquals(recommendListResponseDto.getIngredients(), recipe.getIngredients());
    }
}