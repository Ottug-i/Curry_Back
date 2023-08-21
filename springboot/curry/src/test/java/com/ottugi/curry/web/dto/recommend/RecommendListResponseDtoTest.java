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
        assertEquals(recipe.getRecipeId(), recommendListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), recommendListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), recommendListResponseDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), recommendListResponseDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recommendListResponseDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recommendListResponseDto.getComposition());
        assertEquals(recipe.getIngredients(), recommendListResponseDto.getIngredients());
    }
}