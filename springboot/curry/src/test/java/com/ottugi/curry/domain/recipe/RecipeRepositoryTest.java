package com.ottugi.curry.domain.recipe;

import static com.ottugi.curry.domain.recipe.RecipeTest.INGREDIENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipeRepositoryTest {
    private Recipe recipe;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
        recipe = recipeRepository.save(recipe);
    }

    @AfterEach
    public void clean() {
        recipeRepository.deleteAll();
    }

    @Test
    @DisplayName("레시피 재료로 레시피 목록 조회 테스트")
    void testFindByIngredientsContaining() {
        List<Recipe> foundRecipeList = recipeRepository.findByIngredientsContaining(INGREDIENT);

        assertNotNull(foundRecipeList);
        assertEquals(1, foundRecipeList.size());
        assertTrue(foundRecipeList.get(0).getIngredients().contains(INGREDIENT));
    }

    @Test
    @DisplayName("레시피 이름으로 레시피 목록 조회 테스트")
    void testFindByNameContaining() {
        List<Recipe> foundRecipeList = recipeRepository.findByNameContaining(INGREDIENT);

        assertNotNull(foundRecipeList);
        assertTrue(foundRecipeList.get(0).getName().contains(INGREDIENT));
    }

    @Test
    @DisplayName("레시피 아이디로 레시피 목록 조회 테스트")
    void testFindByRecipeIdIn() {
        List<Long> recipeIdList = List.of(recipe.getRecipeId());
        List<Recipe> foundRecipeList = recipeRepository.findByRecipeIdIn(recipeIdList);

        assertNotNull(foundRecipeList);
        assertEquals(1, foundRecipeList.size());
        assertEquals(recipeIdList.get(0), foundRecipeList.get(0).getRecipeId());
    }

    @Test
    @DisplayName("레시피 기본 키로 레시피 목록 조회 테스트")
    void testFindByIdIn() {
        List<Long> idList = List.of(recipe.getId());
        List<Recipe> foundRecipeList = recipeRepository.findByIdIn(idList);

        assertNotNull(foundRecipeList);
        assertEquals(1, foundRecipeList.size());
        assertEquals(idList.get(0), foundRecipeList.get(0).getId());
    }

    @Test
    @DisplayName("레시피 아이디로 레시피 조회 테스트")
    void testFindByRecipeId() {
        Optional<Recipe> foundRecipeOptional = recipeRepository.findByRecipeId(recipe.getRecipeId());

        assertTrue(foundRecipeOptional.isPresent());
        Recipe foundRecipe = foundRecipeOptional.get();
        assertEquals(recipe.getRecipeId(), foundRecipe.getRecipeId());
    }
}