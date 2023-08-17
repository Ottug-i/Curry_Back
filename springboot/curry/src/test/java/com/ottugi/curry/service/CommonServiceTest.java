package com.ottugi.curry.service;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommonServiceTest {

    private User user;
    private User findUser;
    private Recipe recipe;
    private List<Long> recipeIdList = Arrays.asList(RECIPE_ID);
    private List<Long> idList = Arrays.asList(1L);
    private List<Recipe> recipes = List.of(recipe);
    private List<Recipe> findRecipes;
    private RecipeListResponseDto recipeListResponseDto = new RecipeListResponseDto(recipe, true);
    private List<RecipeListResponseDto> recipeListResponseDtos = List.of(recipeListResponseDto);;
    private Recipe findRecipe;
    private Bookmark bookmark;
    private Bookmark findBookmark;
    private List<Bookmark> bookmarks = List.of(bookmark);
    private List<Bookmark> findBookmarks;

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
        // when
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        findUser = commonService.findByUserId(USER_ID);

        // then
        assertNotNull(findUser);
        assertEquals(findUser, user);
    }

    @Test
    void 회원이메일로회원조회() {
        // when
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        findUser = commonService.findByUserEmail(EMAIL);

        // then
        assertNotNull(findUser);
        assertEquals(findUser, user);
    }

    @Test
    void 레시피아이디로레시피조회() {
        // when
        when(recipeRepository.findByRecipeId(RECIPE_ID)).thenReturn(Optional.of(recipe));

        findRecipe = commonService.findByRecipeId(RECIPE_ID);

        // then
        assertNotNull(findRecipe);
        assertEquals(findRecipe, recipe);
    }

    @Test
    void 레시피아이디리스트로레시피조회() {
        // when
        when(recipeRepository.findByRecipeIdIn(recipeIdList)).thenReturn(recipes);

        findRecipes = commonService.findByRecipeIdIn(recipeIdList);

        // then
        assertNotNull(findRecipes);
        assertEquals(findRecipes.size(), 1);
    }

    @Test
    void 레시피재료로레시피조회() {
        // when
        when(recipeRepository.findByIngredientsContaining(INGREDIENTS)).thenReturn(recipes);

        findRecipes = commonService.findByIngredientsContaining(INGREDIENTS);

        // then
        assertNotNull(findRecipes);
    }

    @Test
    void 레시피기본키로레시피조회() {
        // when
        when(recipeRepository.findByIdIn(idList)).thenReturn(recipes);

        findRecipes = commonService.findByIdIn(idList);

        // then
        assertNotNull(findRecipes);
    }

    @Test
    void 레시피조건일치여부조회() {
        // when
        recipe.setTime(TIME);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setComposition(COMPOSITION);

        // then
        assertTrue(commonService.isRecipeMatching(recipe, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition()));
    }

    @Test
    void 북마크여부조회() {
        // when
        when(bookmarkRepository.findByUserIdAndRecipeId(user, recipe)).thenReturn(bookmark);

        // then
        assertTrue(commonService.isBookmarked(user, recipe));
    }

    @Test
    void 회원기본키와레시피아이디로북마크조회() {
        // when
        when(bookmarkRepository.findByUserIdAndRecipeId(user, recipe)).thenReturn(bookmark);

        findBookmark = commonService.findBookmarkByUserAndRecipe(user, recipe);

        // then
        assertNotNull(findBookmark);
        assertEquals(findBookmark, bookmark);
    }

    @Test
    void 회원기본키로북마크목록조회() {
        // when
        when(bookmarkRepository.findByUserId(user)).thenReturn(bookmarks);

        findBookmarks = commonService.findBookmarkByUser(user);

        // then
        assertNotNull(findBookmarks);
    }

    @Test
    void 페이지처리() {
        // when
        Page<RecipeListResponseDto> response = commonService.getPage(recipeListResponseDtos, PAGE, SIZE);

        assertNotNull(response);
        assertEquals(response.getSize(), SIZE);
        assertEquals(response.getTotalPages(), 1);
    }
}