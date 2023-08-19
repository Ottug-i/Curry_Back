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
        RecommendListResponseDto recommendListResponseDto = new RecommendListResponseDto(recipe);

        // then
        assertEquals(recommendListResponseDto.getRecipeId(), NEW_RECIPE_ID);
        assertEquals(recommendListResponseDto.getName(), NAME);
        assertEquals(recommendListResponseDto.getThumbnail(), THUMBNAIL);
        assertEquals(recommendListResponseDto.getTime(), TIME);
        assertEquals(recommendListResponseDto.getDifficulty(), DIFFICULTY);
        assertEquals(recommendListResponseDto.getComposition(), COMPOSITION);
        assertEquals(recommendListResponseDto.getIngredients(), INGREDIENTS);
    }
}