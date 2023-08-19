package com.ottugi.curry.domain.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeRepositoryTest {

    private String ingredients = "고구마";

    private Recipe recipe;
    private List<Recipe> recipeList;
    private List<Long> recipeIdList = Arrays.asList(EXIST_RECIPE_ID);
    private List<Long> idList = Arrays.asList(1L);

    private RecipeRepository recipeRepository;

    @Autowired
    RecipeRepositoryTest(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Test
    void 레시피재료로조회() {
        // when
        recipeList = recipeRepository.findByIngredientsContaining(ingredients);

        // then
        recipe = recipeList.get(0);
        assertTrue(recipe.getIngredients().contains(ingredients));
    }

    @Test
    void 레시피이름으로조회() {
        // when
        recipeList = recipeRepository.findByNameContaining(ingredients);

        // then
        recipe = recipeList.get(0);
        assertTrue(recipe.getName().contains(ingredients));
    }

    @Test
    void 레시피아이디리스트로조회() {
        // when
        recipeList = recipeRepository.findByRecipeIdIn(recipeIdList);

        // then
        recipe = recipeList.get(0);
        assertEquals(recipe, recipeIdList.get(0));
    }

    @Test
    void 레시피기본키리스트로조회() {
        // when
        recipeList = recipeRepository.findByIdIn(idList);

        // then
        recipe = recipeList.get(0);
        assertEquals(recipe.getRecipeId(), idList.get(0));
    }

    @Test
    void 레시피아이디로조회() {
        // when
        recipe = recipeRepository.findByRecipeId(EXIST_RECIPE_ID).get();

        // then
        assertEquals(recipe.getRecipeId(), EXIST_RECIPE_ID);
    }
}