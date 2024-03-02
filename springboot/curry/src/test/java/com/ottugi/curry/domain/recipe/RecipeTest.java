package com.ottugi.curry.domain.recipe;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeTest {
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        recipe = TestObjectFactory.initRecipe();
    }

    @Test
    @DisplayName("레시피 추가 테스트")
    void testRecipe() {
        assertNotNull(recipe);
        assertEquals(ID, recipe.getId());
        assertEquals(RECIPE_ID, recipe.getRecipeId());
        assertEquals(NAME, recipe.getName());
        assertEquals(THUMBNAIL, recipe.getThumbnail());
        assertEquals(TIME, recipe.getTime());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(COMPOSITION, recipe.getComposition());
        assertEquals(INGREDIENTS, recipe.getIngredients());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(ORDERS, recipe.getOrders());
        assertEquals(PHOTO, recipe.getPhoto());
        assertEquals(GENRE, recipe.getGenre());
    }
}