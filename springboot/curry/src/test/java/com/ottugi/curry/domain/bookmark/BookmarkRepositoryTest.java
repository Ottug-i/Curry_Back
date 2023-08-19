package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookmarkRepositoryTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;
    private Bookmark findBookmark;

    private BookmarkRepository bookmarkRepository;
    private UserRepository userRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    BookmarkRepositoryTest(BookmarkRepository bookmarkRepository, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        user = userRepository.save(user);

        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        recipe = recipeRepository.save(recipe);

        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        bookmark = bookmarkRepository.save(bookmark);
    }

    @AfterEach
    public void clean() {
        // clean
        bookmarkRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 북마크유저이름과레시피아이디로조회() {
        // when
        findBookmark = bookmarkRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(findBookmark.getUserId().getId(), user.getId());
        assertEquals(findBookmark.getRecipeId().getId(), recipe.getId());
    }

    @Test
    void 북마크유저이름으로리스트조회() {
        // when
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);

        // then
        findBookmark = bookmarkList.get(0);
        assertEquals(findBookmark.getUserId().getId(), user.getId());
        assertEquals(findBookmark.getRecipeId().getId(), recipe.getId());
    }
}