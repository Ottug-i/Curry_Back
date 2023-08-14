package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.TestConstants;
import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class BookmarkRepositoryTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private final TestConstants testConstants;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        // given
        user = userRepository.findById(USER_ID).get();
        recipe = recipeRepository.findByRecipeId(RECIPE_ID).get();
    }

    @Test
    void 북마크유저이름과레시피아이디로조회() {
        // when
        bookmark = bookmarkRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(bookmark.getUserId().getId(), USER_ID);
        assertEquals(bookmark.getRecipeId().getRecipeId(), RECIPE_ID);
    }

    @Test
    void 북마크유저이름으로리스트조회() {
        // when
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);

        // then
        bookmark = bookmarkList.get(0);
        assertEquals(bookmark.getUserId().getId(), USER_ID);
        assertEquals(bookmark.getRecipeId().getRecipeId(), RECIPE_ID);
    }
}