package com.ottugi.curry.web.dto.recommend;

import static com.ottugi.curry.domain.recipe.RecipeTest.INGREDIENT;
import static com.ottugi.curry.domain.recipe.RecipeTest.PAGE;
import static com.ottugi.curry.domain.recipe.RecipeTest.SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecipeIngRequestDtoTest {
    public static RecipeIngRequestDto initRecipeIngRequestDto(User user, Recipe recipe) {
        return RecipeIngRequestDto.builder()
                .userId(user.getId())
                .ingredients(List.of(INGREDIENT))
                .time(recipe.getTime().getTimeName())
                .difficulty(recipe.getDifficulty().getDifficulty())
                .composition(recipe.getComposition().getComposition())
                .page(PAGE)
                .size(SIZE)
                .build();
    }

    private Recipe recipe;
    private User user;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("RecipeIngRequestDto 생성 테스트")
    void testRecipeIngRequestDto() {
        RecipeIngRequestDto recipeIngRequestDto = initRecipeIngRequestDto(user, recipe);

        assertEquals(user.getId(), recipeIngRequestDto.getUserId());
        assertEquals(1, recipeIngRequestDto.getIngredients().size());
        assertEquals(INGREDIENT, recipeIngRequestDto.getIngredients().get(0));
        assertEquals(recipe.getTime().getTimeName(), recipeIngRequestDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficulty(), recipeIngRequestDto.getDifficulty());
        assertEquals(recipe.getComposition().getComposition(), recipeIngRequestDto.getComposition());
        assertEquals(PAGE, recipeIngRequestDto.getPage());
        assertEquals(SIZE, recipeIngRequestDto.getSize());
    }
}