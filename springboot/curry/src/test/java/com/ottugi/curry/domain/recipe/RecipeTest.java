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
        recipe = new Recipe(NEW_RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
    }

    @Test
    void 레시피추가() {
        // when, then
        assertEquals(recipe.getRecipeId(), NEW_RECIPE_ID);
        assertEquals(recipe.getName(), NAME);
        assertEquals(recipe.getThumbnail(), THUMBNAIL);
        assertEquals(recipe.getTime(), TIME);
        assertEquals(recipe.getDifficulty(), DIFFICULTY);
        assertEquals(recipe.getComposition(), COMPOSITION);
        assertEquals(recipe.getIngredients(), INGREDIENTS);
        assertEquals(recipe.getServings(), SERVINGS);
        assertEquals(recipe.getOrders(), ORDERS);
        assertEquals(recipe.getPhoto(), PHOTO);
        assertEquals(recipe.getGenre(), GENRE);
    }
}