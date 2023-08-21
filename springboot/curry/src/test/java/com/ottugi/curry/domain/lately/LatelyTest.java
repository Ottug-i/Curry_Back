package com.ottugi.curry.domain.lately;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LatelyTest {

    private User user;
    private Recipe recipe;
    private Lately lately;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        lately = new Lately();
    }

    @Test
    void 최근본레시피추가() {
        // given
        lately.setUser(user);
        lately.setRecipe(recipe);

        // when, then
        assertEquals(user, lately.getUserId());
        assertEquals(recipe, lately.getRecipeId());
    }
}