package com.ottugi.curry.web.dto.lately;

import com.ottugi.curry.domain.recipe.*;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class LatelyListResponseDtoTest {

    private Recipe recipe;

    @Test
    void 최근_본_레시피_응답_Dto_롬복() {
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
        LatelyListResponseDto latelyListResponseDto = new LatelyListResponseDto(recipe);

        // then
        assertEquals(recipe.getRecipeId(), latelyListResponseDto.getRecipeId());
        assertEquals(recipe.getName(), latelyListResponseDto.getName());
        assertEquals(recipe.getThumbnail(), latelyListResponseDto.getThumbnail());
    }
}