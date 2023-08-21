package com.ottugi.curry.domain.recipe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeRepositoryTest {

    private Recipe recipe;

    private String ingredient = "고구마";
    private List<Long> idList = new ArrayList<>();
    private List<Long> recipeIdList = new ArrayList<>();
    private List<Recipe> recipeList;

    private RecipeRepository recipeRepository;

    private Recipe testRecipe;

    @Autowired
    RecipeRepositoryTest(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        recipe = recipeRepository.save(recipe);
    }

    @AfterEach
    public void clean() {
        // clean
        recipeRepository.deleteAll();
    }

    @Test
    void 레시피재료로조회() {
        // when
        recipeList = recipeRepository.findByIngredientsContaining(ingredient);

        // then
        testRecipe = recipeList.get(0);
        assertTrue(testRecipe.getIngredients().contains(ingredient));
    }

    @Test
    void 레시피이름으로조회() {
        // when
        recipeList = recipeRepository.findByNameContaining(ingredient);

        // then
        testRecipe = recipeList.get(0);
        assertTrue(testRecipe.getName().contains(ingredient));
    }

    @Test
    void 레시피아이디리스트로조회() {
        // given
        recipeIdList.add(recipe.getRecipeId());

        // when
        recipeList = recipeRepository.findByRecipeIdIn(recipeIdList);

        // then
        testRecipe = recipeList.get(0);
        assertEquals(recipeIdList.get(0), testRecipe.getRecipeId());
    }

    @Test
    void 레시피기본키리스트로조회() {
        // given
        idList.add(recipe.getId());

        // when
        recipeList = recipeRepository.findByIdIn(idList);

        // then
        testRecipe = recipeList.get(0);
        assertEquals(idList.get(0), testRecipe.getId());
    }

    @Test
    void 레시피아이디로조회() {
        // when
        testRecipe = recipeRepository.findByRecipeId(recipe.getRecipeId()).get();

        // then
        assertEquals(recipe.getRecipeId(), testRecipe.getRecipeId());
    }
}