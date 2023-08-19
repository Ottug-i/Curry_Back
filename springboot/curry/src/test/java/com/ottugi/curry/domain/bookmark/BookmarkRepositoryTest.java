package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
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
        // given (이미 DB에 저장되어있는 데이터 사용)
        user = userRepository.findById(USER_ID).get();
        recipe = recipeRepository.findByRecipeId(EXIST_RECIPE_ID).get();
    }

    @Test
    void 북마크유저이름과레시피아이디로조회() {
        // when
        bookmark = bookmarkRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(bookmark.getUserId().getId(), USER_ID);
        assertEquals(bookmark.getRecipeId().getRecipeId(), EXIST_RECIPE_ID);
    }

    @Test
    void 북마크유저이름으로리스트조회() {
        // when
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);

        // then
        bookmark = bookmarkList.get(0);
        assertEquals(bookmark.getUserId().getId(), USER_ID);
        assertEquals(bookmark.getRecipeId().getRecipeId(), EXIST_RECIPE_ID);
    }
}