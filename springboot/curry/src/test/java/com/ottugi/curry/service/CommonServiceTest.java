package com.ottugi.curry.service;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommonServiceTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @InjectMocks
    private CommonService commonService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User();
        recipe = new Recipe();
        bookmark = new Bookmark();
    }

    @Test
    void 회원기본키로회원조회() {
    }

    @Test
    void 회원이메일로회원조회() {
    }

    @Test
    void 레시피아이디로레시피조회() {
    }

    @Test
    void 레시피아이디리스트로레시피조회() {
    }

    @Test
    void 레시피재료로레시피조회() {
    }

    @Test
    void 레시피기본키로레시피조회() {
    }

    @Test
    void 레시피조건일치여부조회() {
    }

    @Test
    void 북마크여부조회() {
    }

    @Test
    void 회원기본키와레시피아이디로북마크조회() {
    }

    @Test
    void 회원기본키로북마크목록조회() {
    }

    @Test
    void 페이지처리() {
    }
}