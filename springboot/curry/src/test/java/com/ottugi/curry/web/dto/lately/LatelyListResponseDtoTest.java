package com.ottugi.curry.web.dto.lately;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class LatelyListResponseDtoTest {

    private Recipe recipe;

    @Test
    void LatelyListResponseDto_롬복() {
        // given
        recipe = Recipe.builder()
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
        LatelyListResponseDto latelyListResponseDto = new LatelyListResponseDto(recipe);

        // then
        assertEquals(latelyListResponseDto.getRecipeId(), RECIPE_ID);
        assertEquals(latelyListResponseDto.getName(), NAME);
        assertEquals(latelyListResponseDto.getThumbnail(), THUMBNAIL);
    }
}