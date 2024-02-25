package com.ottugi.curry.service.recommend;

import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.EMAIL;
import static com.ottugi.curry.TestConstants.FAVORITE_GENRE;
import static com.ottugi.curry.TestConstants.GENRE;
import static com.ottugi.curry.TestConstants.ID;
import static com.ottugi.curry.TestConstants.INGREDIENTS;
import static com.ottugi.curry.TestConstants.NAME;
import static com.ottugi.curry.TestConstants.NICKNAME;
import static com.ottugi.curry.TestConstants.ORDERS;
import static com.ottugi.curry.TestConstants.PAGE;
import static com.ottugi.curry.TestConstants.PHOTO;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.ROLE;
import static com.ottugi.curry.TestConstants.SERVINGS;
import static com.ottugi.curry.TestConstants.SIZE;
import static com.ottugi.curry.TestConstants.THUMBNAIL;
import static com.ottugi.curry.TestConstants.TIME;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RecommendServiceTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private final Boolean isBookmark = true;

    private final String ingredient = "고구마";
    private final List<String> ingredientList = new ArrayList<>();
    private final List<Long> idList = new ArrayList<>();
    private final List<Long> recipeIdList = new ArrayList<>();
    private final List<Recipe> recipeList = new ArrayList<>();
    private final List<RecipeIngListResponseDto> recipeIngListResponseDtoList = new ArrayList<>();
    private Page<RecipeIngListResponseDto> recipeIngListResponseDtoListPage;

    private Long[] bookmarkIdList;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private GlobalConfig config;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendServiceImpl recommendService;

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
        ingredientList.add(ingredient);
        recipeIngListResponseDtoList.add(new RecipeIngListResponseDto(ingredientList, recipe, isBookmark));
        recipeIngListResponseDtoListPage = new PageImpl<>(recipeIngListResponseDtoList, PageRequest.of(PAGE - 1, SIZE),
                recipeIngListResponseDtoList.size());

        bookmarkIdList = new Long[]{bookmark.getId()};
    }

    @AfterEach
    public void clean() {
        // clean
        bookmarkRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 재료로_레시피_목록_조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(commonService.findByIngredientsContaining(anyString())).thenReturn(recipeList);
        when(commonService.isRecipeMatching(any(Recipe.class), anyString(), anyString(), anyString())).thenReturn(true);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);
        doReturn(recipeIngListResponseDtoListPage).when(commonService).getPage(anyList(), anyInt(), anyInt());

        // when
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto(user.getId(), ingredientList, TIME.getTimeName(), DIFFICULTY.getDifficulty(),
                COMPOSITION.getComposition(), PAGE, SIZE);
        Page<RecipeIngListResponseDto> testRecipeIngListResponseDtoListPage = recommendService.findRecipePageByIngredientsDetection(recipeRequestDto);

        // then
        assertNotNull(testRecipeIngListResponseDtoListPage);
        assertEquals(recipeIngListResponseDtoListPage.getTotalElements(), testRecipeIngListResponseDtoListPage.getTotalElements());
    }

    @Test
    void 북마크_추천_아이디_목록_조회() {
        // given
        String httpResponse = "[1]";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(httpResponse);

        List<Long> testRecommendRecipeIdList = recommendService.findRecipeIdListByBookmarkRecommend(recipe.getRecipeId(), PAGE);

        // then
        assertNotNull(testRecommendRecipeIdList);
        assertEquals(1, testRecommendRecipeIdList.size());
    }


    @Test
    void 평점_추천_아이디_목록_조회() {
        // given
        String httpResponse = "[\"ing15\", [1]]";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(httpResponse);
        when(commonService.findByUserId(anyLong())).thenReturn(user);

        // when
        List<Long> testRecommendRecipeIdList = recommendService.findRecipeIdListByRatingRecommend(user.getId(), PAGE, bookmarkIdList);

        // then
        assertNotNull(testRecommendRecipeIdList);
        assertEquals(1, testRecommendRecipeIdList.size());
    }

    @Test
    void 북마크_및_평점_추천_레시피_목록_조회() {
        // given
        when(commonService.findByUserId(anyLong())).thenReturn(user);
        when(commonService.findByRecipeIdIn(anyList())).thenReturn(recipeList);
        when(commonService.isBookmarked(any(User.class), any(Recipe.class))).thenReturn(true);

        // when
        RecommendRequestDto recommendRequestDto = new RecommendRequestDto(user.getId(), recipeIdList);
        List<RecipeListResponseDto> testRecipeListResponseDtoList = recommendService.findBookmarkOrRatingRecommendList(recommendRequestDto);

        // then
        assertNotNull(testRecipeListResponseDtoList);
        assertEquals(recipeList.size(), testRecipeListResponseDtoList.size());
    }
}