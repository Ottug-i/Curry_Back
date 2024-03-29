package com.ottugi.curry.domain.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookmarkRepositoryTest {
    private Bookmark bookmark;

    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        bookmark = BookmarkTest.initBookmark();
        bookmark.setUser(userRepository.save(UserTest.initUser()));
        bookmark.setRecipe(recipeRepository.save(RecipeTest.initRecipe()));
        bookmark = bookmarkRepository.save(bookmark);
    }

    @AfterEach
    public void clean() {
        bookmarkRepository.deleteAll();
        userRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 북마크 존재 확인 테스트")
    void testExistsByUserIdAndRecipeId() {
        boolean existBookmark = bookmarkRepository.existsByUserIdAndRecipeId(bookmark.getUserId(), bookmark.getRecipeId());

        assertTrue(existBookmark);
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 북마크 삭제 테스트")
    void testDeleteByUserIdAndRecipeId() {
        bookmarkRepository.deleteByUserIdAndRecipeId(bookmark.getUserId(), bookmark.getRecipeId());
        boolean existBookmark = bookmarkRepository.existsByUserIdAndRecipeId(bookmark.getUserId(), bookmark.getRecipeId());

        assertFalse(existBookmark);
    }
}