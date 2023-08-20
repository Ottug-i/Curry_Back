package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.bookmark.Bookmark;
import com.ottugi.curry.domain.bookmark.BookmarkRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.bookmark.BookmarkListResponseDto;
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

    private String FLASK_API_URL = "http://localhost:5000";
    private String apiUrl;

    private Double[] ratings = {3.5};
    private HttpHeaders headers = new HttpHeaders();

    private User user;
    private Recipe recipe;
    private Bookmark bookmark;

    private RecipeRequestDto recipeRequestDto;
    private RatingResponseDto ratingResponseDto;
    private RatingRequestDto ratingRequestDto;
    private RecommendRequestDto recommendRequestDto;

    private Map<Long, Double> newUserRatingsDic = new HashMap<>();
    private List<String> ingredients = new ArrayList<>();
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Long> recipeIdList = new ArrayList<>();
    private List<RecommendListResponseDto> recommendListResponseDtoList = new ArrayList<>();
    private List<RecipeIngListResponseDto> recipeIngListResponseDtoList = new ArrayList<>();
    private Long[] bookmarkIdList;

    private long min = 1L;
    private long max = 3616L;
    private int batchSize = 10;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private CommonService commonService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendServiceImpl recommendService;

    @BeforeEach
    public void setUp() {
        // given
        user = new User(USER_ID, EMAIL, NICKNAME, FAVORITE_GENRE, ROLE);
        when(userRepository.save(eq(user))).thenReturn(user);

        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        when(recipeRepository.save(eq(recipe))).thenReturn(recipe);

        bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setRecipe(recipe);
        when(bookmarkRepository.save(eq(bookmark))).thenReturn(bookmark);

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
    void 랜덤레시피목록조회() {
        // when
        when(commonService.findByIdIn(recipeIdList)).thenReturn(recipeList);

        recommendListResponseDtoList = recommendService.getRandomRecipe();

        // then
        assertNotNull(recommendListResponseDtoList);
    }

    @Test
    void 평점조회() throws JsonProcessingException { /** 수정 필요 **/
        // given
        apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, user.getId(), recipe.getRecipeId());
        String response = objectMapper.writeValueAsString(ratings);

        // when
        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(response);

        ratingResponseDto = recommendService.getUserRating(user.getId(), recipe.getRecipeId());

        // then
        assertNotNull(ratingResponseDto);
    }

    @Test
    void 평점업데이트() {
        // given
        apiUrl = String.format("%s/rating/user_ratings", FLASK_API_URL);

        headers.setContentType(MediaType.APPLICATION_JSON);

        ratingRequestDto = new RatingRequestDto(user.getId(), newUserRatingsDic);
        newUserRatingsDic.put(6847060L, 3.0);

        HttpEntity<RatingRequestDto> httpEntity = new HttpEntity<>(ratingRequestDto, headers);

        ResponseEntity<String> responseEntity = ResponseEntity.ok("true");

        // when
        when(restTemplate.postForEntity(apiUrl, httpEntity, String.class)).thenReturn(responseEntity);

        boolean result = recommendService.updateUserRating(ratingRequestDto);

        // then
        assertTrue(result);
    }

    @Test
    void 평점삭제() {
        // given
        apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, user.getId(), recipe.getRecipeId());

        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();

        // when
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.DELETE), any(), eq(Void.class))).thenReturn(responseEntity);

        boolean result = recommendService.deleteUserRating(user.getId(), recipe.getRecipeId());

        // then
        assertTrue(result);
    }

    @Test
    void 재료추천목록조회() {
        // given
        ingredients.add("고구마");
        recipeRequestDto = new RecipeRequestDto(user.getId(), ingredients, PAGE, SIZE);
        Page<RecipeIngListResponseDto> recommendIngListResponseDtoListPage = new PageImpl<>(recipeIngListResponseDtoList, PageRequest.of(PAGE - 1, SIZE), recipeList.size());

        // when
        when(commonService.findByUserId(recipeRequestDto.getUserId())).thenReturn(user);
        when(commonService.findByIngredientsContaining(recipeRequestDto.getIngredients().get(0))).thenReturn(recipeList);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);
        when(commonService.getPage(recipeIngListResponseDtoList, PAGE, SIZE)).thenReturn(recommendIngListResponseDtoListPage);

        Page<RecipeIngListResponseDto> response = recommendService.getIngredientsRecommendList(recipeRequestDto);

        // then
        assertNotNull(response);
    }

    @Test
    void 북마크추천아이디목록조회() throws JsonProcessingException {
        // given
        apiUrl = String.format("%s/bookmark/recommend?recipe_id=%d&page=%d", FLASK_API_URL, recipe.getRecipeId(), PAGE);

        String responseJson = objectMapper.writeValueAsString(recipeIdList);

        // when
        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(responseJson);

        List<Long> result = recommendService.getRecommendBookmarkId(recipe.getRecipeId(), PAGE);

        // then
        assertNotNull(result);
        assertEquals(result, recipeIdList);
    }


    @Test
    void 평점추천목록아이디조회() throws JsonProcessingException { /** 수정 필요 **/
        // given
        apiUrl = String.format("%s/rating/recommend?user_id=%d&page=%d", FLASK_API_URL, user.getId(), PAGE);
        if(bookmarkIdList != null) {
            for (Long bookmarkId : bookmarkIdList) {
                apiUrl += "&bookmark_list=" + bookmarkId;
            }
        }

        String responseJson = objectMapper.writeValueAsString(recipeIdList);

        // when
        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(responseJson);
        when(commonService.findByUserId(user.getId())).thenReturn(user);

        List<Long> result = recommendService.getRecommendRatingId(user.getId(), PAGE, bookmarkIdList);

        // then
        assertNotNull(result);
        assertEquals(result, recipeIdList);
    }

    @Test
    void 북마크및평점레시피목록조회() {
        // given
        recommendRequestDto = new RecommendRequestDto(user.getId(), recipeIdList);

        // when
        when(commonService.findByUserId(user.getId())).thenReturn(user);
        when(commonService.findByRecipeIdIn(recipeIdList)).thenReturn(recipeList);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);

        List<RecipeListResponseDto> recipeListResponseDtoList = recommendService.getBookmarkOrRatingRecommendList(recommendRequestDto);

        // then
        assertNotNull(recipeListResponseDtoList);
        assertEquals(recipeList.size(), recipeListResponseDtoList.size());
    }
}