package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.config.GlobalConfig;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecommendServiceTest {

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private Boolean isBookmark = true;

    private String ingredient = "고구마";
    private List<String> ingredientList = new ArrayList<>();
    private List<Long> idList = new ArrayList<>();
    private List<Long> recipeIdList = new ArrayList<>();
    private List<Recipe> recipeList = new ArrayList<>();
    private List<RecipeIngListResponseDto> recipeIngListResponseDtoList = new ArrayList<>();
    private Page<RecipeIngListResponseDto> recipeIngListResponseDtoListPage;

    private Long[] bookmarkIdList;

    private Map<Long, Double> newUserRatingsDic = new HashMap<>();

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
        recipeIngListResponseDtoListPage = new PageImpl<>(recipeIngListResponseDtoList, PageRequest.of(PAGE - 1, SIZE), recipeIngListResponseDtoList.size());

        bookmarkIdList = new Long[]{bookmark.getId()};

        newUserRatingsDic.put(6847060L, 3.0);
    }

    @AfterEach
    public void clean() {
        // clean
        bookmarkRepository.deleteAll();
        recipeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 랜덤_레시피_목록_조회() {
        // given
        when(commonService.findByIdIn(anyList())).thenReturn(recipeList);

        // when
        List<RecommendListResponseDto> testRecommendListResponseDtoList = recommendService.getRandomRecipe();

        // then
        assertNotNull(testRecommendListResponseDtoList);
        assertEquals(idList.size(), testRecommendListResponseDtoList.size());
    }

    @Test
    void 레시피_평점_조회() {
        // given
        String httpResponse = "[" + Double.valueOf(RECIPE_ID) + ", " + Double.valueOf(USER_ID) + ", " +  3.5 + "]";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(httpResponse);

        // when
        RatingResponseDto testRatingResponseDto = recommendService.getUserRating(user.getId(), recipe.getRecipeId());

        // then
        assertNotNull(testRatingResponseDto);
        assertEquals(user.getId(), testRatingResponseDto.getUserId());
        assertEquals(recipe.getRecipeId(), testRatingResponseDto.getRecipeId());
    }

    @Test
    void 레시피_평점_업데이트() {
        // given
        ResponseEntity<String> updateHttpResponseEntity = ResponseEntity.ok("true");
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class))).thenReturn(updateHttpResponseEntity);

        // when
        RatingRequestDto ratingRequestDto = new RatingRequestDto(user.getId(), newUserRatingsDic);
        Boolean testResponse = recommendService.updateUserRating(ratingRequestDto);

        // then
        assertTrue(testResponse);
    }

    @Test
    void 레시피_평점_삭제() {
        // given
        ResponseEntity<Void> deleteHttpResponseEntity = ResponseEntity.noContent().build();
        when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(), eq(Void.class))).thenReturn(deleteHttpResponseEntity);

        // when
        Boolean testResponse = recommendService.deleteUserRating(user.getId(), recipe.getRecipeId());

        // then
        assertTrue(testResponse);
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
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto(user.getId(), ingredientList, TIME.getTimeName(), DIFFICULTY.getDifficulty(), COMPOSITION.getComposition(), PAGE, SIZE);
        Page<RecipeIngListResponseDto> testRecipeIngListResponseDtoListPage = recommendService.getIngredientsRecommendList(recipeRequestDto);

        // then
        assertNotNull(testRecipeIngListResponseDtoListPage);
        assertEquals(recipeIngListResponseDtoListPage.getTotalElements(), testRecipeIngListResponseDtoListPage.getTotalElements());
    }

    @Test
    void 북마크_추천_아이디_목록_조회() throws JsonProcessingException {
        // given
        String httpResponse = "[1]";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(httpResponse);

        List<Long> testRecommendRecipeIdList = recommendService.getRecommendBookmarkId(recipe.getRecipeId(), PAGE);

        // then
        assertNotNull(testRecommendRecipeIdList);
        assertEquals(1, testRecommendRecipeIdList.size());
    }


    @Test
    void 평점_추천_아이디_목록_조회() throws JsonProcessingException {
        // given
        String httpResponse = "[\"ing15\", [1]]";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(httpResponse);
        when(commonService.findByUserId(anyLong())).thenReturn(user);

        // when
        List<Long> testRecommendRecipeIdList = recommendService.getRecommendRatingId(user.getId(), PAGE, bookmarkIdList);

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
        List<RecipeListResponseDto> testRecipeListResponseDtoList = recommendService.getBookmarkOrRatingRecommendList(recommendRequestDto);

        // then
        assertNotNull(testRecipeListResponseDtoList);
        assertEquals(recipeList.size(), testRecipeListResponseDtoList.size());
    }
}