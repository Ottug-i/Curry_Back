package com.ottugi.curry.domain.bookmark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookmarkRepositoryTest {
    private User user;
    private Recipe recipe;

    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        user = TestObjectFactory.initUser();
        user = userRepository.save(user);

        recipe = TestObjectFactory.initRecipe();
        recipe = recipeRepository.save(recipe);

        Bookmark bookmark = TestObjectFactory.initBookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        bookmarkRepository.save(bookmark);
    }

    @AfterEach
    public void clean() {
        bookmarkRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 북마크 존재 확인 테스트")
    void testExistsByUserIdAndRecipeId() {
        boolean existBookmark = bookmarkRepository.existsByUserIdAndRecipeId(user, recipe);

        assertTrue(existBookmark);
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 북마크 조회 테스트")
    void testFindByUserIdAndRecipeId() {
        Bookmark foundBookmark = bookmarkRepository.findByUserIdAndRecipeId(user, recipe);

        assertEquals(user.getId(), foundBookmark.getUserId().getId());
        assertEquals(recipe.getId(), foundBookmark.getRecipeId().getId());
    }

    @Test
    @DisplayName("회원 아이디로 북마크 목록 조회 테스트")
    void testFindByUserId() {
        List<Bookmark> foundBookmarkList = bookmarkRepository.findByUserId(user);

        assertNotNull(foundBookmarkList);
        assertEquals(1, foundBookmarkList.size());
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 북마크 삭제 테스트")
    void testDeleteByUserIdAndRecipeId() {
        bookmarkRepository.deleteByUserIdAndRecipeId(user, recipe);
        boolean existBookmark = bookmarkRepository.existsByUserIdAndRecipeId(user, recipe);

        assertFalse(existBookmark);
    }
}