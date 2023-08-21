package com.ottugi.curry.domain.recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        // given
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
    }

    @Test
    void 레시피추가() {
        // when, then
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