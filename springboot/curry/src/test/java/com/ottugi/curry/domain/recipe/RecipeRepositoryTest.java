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

    private String ingredient = "고구마";

    private Recipe recipe;
    private List<Recipe> recipeList;
    private List<Long> recipeIdList = new ArrayList<>();
    private List<Long> idList = new ArrayList<>();

    private RecipeRepository recipeRepository;

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
        recipe = recipeList.get(0);
        assertTrue(recipe.getIngredients().contains(ingredient));
    }

    @Test
    void 레시피이름으로조회() {
        // when
        recipeList = recipeRepository.findByNameContaining(ingredient);

        // then
        recipe = recipeList.get(0);
        assertTrue(recipe.getName().contains(ingredient));
    }

    @Test
    void 레시피아이디리스트로조회() {
        // given
        recipeIdList.add(recipe.getRecipeId());

        // when
        recipeList = recipeRepository.findByRecipeIdIn(recipeIdList);

        // then
        recipe = recipeList.get(0);
        assertEquals(recipe.getRecipeId(), recipeIdList.get(0));
    }

    @Test
    void 레시피기본키리스트로조회() {
        // given
        idList.add(recipe.getId());

        // when
        recipeList = recipeRepository.findByIdIn(idList);

        // then
        recipe = recipeList.get(0);
        assertEquals(recipe.getId(), idList.get(0));
    }

    @Test
    void 레시피아이디로조회() {
        // when
        recipe = recipeRepository.findByRecipeId(RECIPE_ID).get();

        // then
        assertEquals(recipe.getRecipeId(), RECIPE_ID);
    }
}