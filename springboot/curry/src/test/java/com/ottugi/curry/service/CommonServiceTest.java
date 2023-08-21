package com.ottugi.curry.service;

import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommonServiceTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private Boolean isBookmark = true;

    private String ingredient = "고구마";
    private List<Long> idList = new ArrayList<>();
    private List<Long> recipeIdList = new ArrayList<>();
    private List<Recipe> recipeList = new ArrayList<>();
    private List<RecipeListResponseDto> recipeListResponseDtoList = new ArrayList<>();

    private List<Bookmark> bookmarkList = new ArrayList<>();

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
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(bookmark);

        idList.add(recipe.getId());
        recipeIdList.add(recipe.getRecipeId());
        recipeList.add(recipe);
        bookmarkList.add(bookmark);
        recipeListResponseDtoList.add(new RecipeListResponseDto(recipe, isBookmark));
    }

    @AfterEach
    public void clean() {
        // clean
        bookmarkRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 회원기본키로회원조회() {
        // given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // when
        User testUser = commonService.findByUserId(user.getId());

        // then
        assertNotNull(testUser);
        assertEquals(user, testUser);
    }

    @Test
    void 회원이메일로회원조회() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when
        User testUser = commonService.findByUserEmail(user.getEmail());

        // then
        assertNotNull(testUser);
        assertEquals(user, testUser);
    }

    @Test
    void 레시피아이디로레시피조회() {
        // given
        when(recipeRepository.findByRecipeId(anyLong())).thenReturn(Optional.of(recipe));

        // when
        Recipe testRecipe = commonService.findByRecipeId(recipe.getRecipeId());

        // then
        assertNotNull(testRecipe);
        assertEquals(recipe, testRecipe);
    }

    @Test
    void 레시피아이디리스트로레시피조회() {
        // given
        when(recipeRepository.findByRecipeIdIn(anyList())).thenReturn(recipeList);

        // when
        List<Recipe> testRecipeList = commonService.findByRecipeIdIn(recipeIdList);

        // then
        assertNotNull(testRecipeList);
        assertEquals(recipeList.size(), testRecipeList.size());
    }

    @Test
    void 레시피재료로레시피조회() {
        // given
        when(recipeRepository.findByIngredientsContaining(anyString())).thenReturn(recipeList);

        List<Recipe> testRecipeList = commonService.findByIngredientsContaining(ingredient);

        // then
        assertNotNull(testRecipeList);
        assertTrue(testRecipeList.get(0).getIngredients().contains(ingredient));
    }

    @Test
    void 레시피기본키로레시피리스트조회() {
        // given
        when(recipeRepository.findByIdIn(anyList())).thenReturn(recipeList);

        List<Recipe> testRecipeList = commonService.findByIdIn(idList);

        // then
        assertNotNull(testRecipeList);
        assertEquals(recipeList.size(), testRecipeList.size());
    }

    @Test
    void 레시피조건일치여부조회() {
        // when
        Boolean testResponse = commonService.isRecipeMatching(recipe, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition());

        // then
        assertTrue(testResponse);
    }

    @Test
    void 북마크여부조회() {
        // given
        when(bookmarkRepository.findByUserIdAndRecipeId(any(User.class), any(Recipe.class))).thenReturn(bookmark);

        // when
        Boolean testResponse = commonService.isBookmarked(user, recipe);

        // then
        assertTrue(testResponse);
    }

    @Test
    void 회원기본키와레시피아이디로북마크조회() {
        // given
        when(bookmarkRepository.findByUserIdAndRecipeId(any(User.class), any(Recipe.class))).thenReturn(bookmark);

        // when
        Bookmark testBookmark = commonService.findBookmarkByUserAndRecipe(user, recipe);

        // then
        assertNotNull(testBookmark);
        assertEquals(bookmark, testBookmark);
    }

    @Test
    void 회원기본키로북마크목록조회() {
        // given
        when(bookmarkRepository.findByUserId(any(User.class))).thenReturn(bookmarkList);

        // when
        List<Bookmark> testBookmarkList = commonService.findBookmarkByUser(user);

        // then
        assertNotNull(testBookmarkList);
        assertEquals(bookmarkList.size(), testBookmarkList.size());
    }

    @Test
    void 페이지처리() {
        // when
        Page<RecipeListResponseDto> recipeListPageResponseDto = commonService.getPage(recipeListResponseDtoList, PAGE, SIZE);

        assertNotNull(recipeListPageResponseDto);
        assertEquals(recipeListResponseDtoList.size(), recipeListPageResponseDto.getSize());
    }
}