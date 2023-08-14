package com.ottugi.curry.domain.recipe;

import com.ottugi.curry.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class RecipeTest {

    private Recipe recipe;

    private final TestConstants testConstants;

    @BeforeEach
    public void setUp() {
        // given
        recipe = new Recipe(1234L, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
    }

    @Test
    void 레시피추가() {
        // when, then
        assertEquals(recipe.getId(), 1234L);
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