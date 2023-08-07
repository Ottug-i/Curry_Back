package com.ottugi.curry.domain.bookmark;

import com.ottugi.curry.domain.recipe.*;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookmarkRepositoryTest {

    // 이미 DB에 저장되어있는 데이터 사용
    private final Long userId = 1L;
    private final Long recipeId = 6842324L;

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

        // given
        user = userRepository.findById(userId).orElseThrow();
        recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow();
    }

    @Test
    void 북마크유저이름과레시피아이디로조회() {

        // when
        Bookmark findBookmark = bookmarkRepository.findByUserIdAndRecipeId(user, recipe);

        // then
        assertEquals(findBookmark.getUserId().getId(), userId);
        assertEquals(findBookmark.getRecipeId().getRecipeId(), recipeId);
    }

    @Test
    void 북마크유저이름으로리스트조회() {

        // when
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(user);

        // then
        Bookmark findBookmark = bookmarkList.get(0);
        assertEquals(findBookmark.getUserId().getId(), userId);
        assertEquals(findBookmark.getRecipeId().getRecipeId(), recipeId);
    }
}