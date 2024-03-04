package com.ottugi.curry.web.dto.recommend;

import static com.ottugi.curry.TestConstants.INGREDIENT1;
import static com.ottugi.curry.TestConstants.INGREDIENT2;
import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeIngRequestDtoTest {
    private Recipe recipe;
    private User user;

    @BeforeEach
    public void setUp() {
        recipe = TestObjectFactory.initRecipe();
        user = TestObjectFactory.initUser();
    }

    @Test
    @DisplayName("RecipeIngRequestDto 생성 테스트")
    void testRecipeIngRequestDto() {
        RecipeIngRequestDto recipeIngRequestDto = RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(Collections.singletonList(INGREDIENT1))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(SIZE)
                .build();

        assertEquals(user.getId(), recipeIngRequestDto.getUserId());
        assertEquals(2, recipeIngRequestDto.getIngredients().size());
        assertEquals(INGREDIENT1, recipeIngRequestDto.getIngredients().get(0));
        assertEquals(INGREDIENT2, recipeIngRequestDto.getIngredients().get(1));
        assertEquals(recipe.getTime().getTimeName(), recipeIngRequestDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeIngRequestDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeIngRequestDto.getComposition());
        assertEquals(PAGE, recipeIngRequestDto.getPage());
        assertEquals(SIZE, recipeIngRequestDto.getSize());
    }
}