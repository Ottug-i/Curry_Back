package com.ottugi.curry.domain.lately;

import com.ottugi.curry.TestConstants;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class LatelyTest {

    private User user;
    private Recipe recipe;
    private Lately lately;

    private final TestConstants testConstants;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(1234L, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
    }

    @Test
    void 최근본레시피추가() {
        // given
        lately = new Lately();
        lately.setUser(user);
        lately.setRecipe(recipe);

        // when, then
        assertEquals(lately.getUserId(), user);
        assertEquals(lately.getRecipeId(), recipe);
    }
}