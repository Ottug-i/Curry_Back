package com.ottugi.curry.web.dto.recommend;

import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.GENRE;
import static com.ottugi.curry.TestConstants.ID;
import static com.ottugi.curry.TestConstants.INGREDIENTS;
import static com.ottugi.curry.TestConstants.NAME;
import static com.ottugi.curry.TestConstants.ORDERS;
import static com.ottugi.curry.TestConstants.PHOTO;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.SERVINGS;
import static com.ottugi.curry.TestConstants.THUMBNAIL;
import static com.ottugi.curry.TestConstants.TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecipeIngListResponseDtoTest {

    private Recipe recipe;
    private final Boolean isBookmark = true;
    private final List<String> ingredients = List.of("고구마");

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