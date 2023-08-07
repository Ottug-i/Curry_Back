package com.ottugi.curry.domain.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeRepositoryTest {

    // 이미 DB에 저장되어있는 데이터 사용
    private final Long recipeId = 6842324L;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void 레시피재료로조회() {

        String ingredients = "고구마";
        List<Recipe> recipeList = recipeRepository.findByIngredientsContaining(ingredients);

        assertNotNull(recipeList);
    }

    @Test
    void 레시피이름으로조회() {

        String ingredients = "고구마";
        List<Recipe> recipeList = recipeRepository.findByNameContaining(ingredients);

        assertNotNull(recipeList);
    }

    @Test
    void 레시피아이디로조회() {

        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow();

        assertEquals(recipe.getRecipeId(), recipeId);
    }
}