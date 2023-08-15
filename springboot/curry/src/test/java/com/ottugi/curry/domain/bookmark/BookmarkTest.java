package com.ottugi.curry.domain.bookmark;

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
class BookmarkTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private final TestConstants testConstants;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
    }

    @Test
    void 북마크추가() {
        // given
        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        // when, then
        assertEquals(bookmark.getUserId(), user);
        assertEquals(bookmark.getRecipeId(), recipe);
    }
}