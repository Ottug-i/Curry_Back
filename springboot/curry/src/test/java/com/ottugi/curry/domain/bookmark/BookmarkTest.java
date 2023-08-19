package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookmarkTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        bookmark = new Bookmark();
    }

    @Test
    void 북마크추가() {
        // given
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        // when, then
        assertEquals(bookmark.getUserId(), user);
        assertEquals(bookmark.getRecipeId(), recipe);
    }
}