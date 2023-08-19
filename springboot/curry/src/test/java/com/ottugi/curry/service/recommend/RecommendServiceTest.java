package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RecommendServiceTest {

    private String FLASK_API_URL = "http://localhost:5000";
    private String apiUrl;

    private Double[] ratings = {3.5, 4.0};
    private HttpHeaders headers;

    private User user;
    private Recipe recipe;
    private RecipeRequestDto recipeRequestDto;
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Long> recipeIdList = new ArrayList<>();
    private List<RecommendListResponseDto> recommendListResponseDtos = new ArrayList<>();
    private RatingResponseDto ratingResponseDto;
    private RatingRequestDto ratingRequestDto;
    private Long[] bookmarkIdList = new Long[]{};
    private RecommendRequestDto recommendRequestDto;

    private long min = 1L;
    private long max = 3616L;
    private int batchSize = 10;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CommonService commonService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendService recommendService;

    RecommendServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        // given
        headers = new HttpHeaders();
        user = new User();
        recipe = new Recipe();
    }

    @Test
    void 랜덤레시피목록조회() {
        // given
        for (long id : ThreadLocalRandom.current().longs(min, max + 1).distinct().limit(batchSize).toArray()) {
            recipeIdList.add(id);
        }

        // when
        when(commonService.findByIdIn(recipeIdList)).thenReturn(recipeList);

        recommendListResponseDtos = recommendService.getRandomRecipe();

        // then
        assertNotNull(recommendListResponseDtos);
        assertEquals(recommendListResponseDtos.size(), batchSize);
    }

    @Test
    void 평점조회() throws JsonProcessingException {
        // given
        apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, USER_ID, RECIPE_ID);
        String response = objectMapper.writeValueAsString(ratings);

        // when
        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(response);

        ratingResponseDto = recommendService.getUserRating(USER_ID, RECIPE_ID);

        // then
        assertNotNull(ratingResponseDto);
        assertEquals(Arrays.asList(ratings), ratingResponseDto.getRating());
    }

    @Test
    void 평점업데이트() {
        // given
        apiUrl = String.format("%s/rating/user_ratings", FLASK_API_URL);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

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
        apiUrl = String.format("%s/rating/user_ratings?user_id=%d&recipe_id=%d", FLASK_API_URL, USER_ID, RECIPE_ID);

        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();

        // when
        when(restTemplate.exchange(eq(apiUrl), eq(HttpMethod.DELETE), any(), eq(Void.class))).thenReturn(responseEntity);

        boolean result = recommendService.deleteUserRating(USER_ID, RECIPE_ID);

        // then
        assertTrue(result);
    }

    @Test
    void 재료추천목록조회() {
        // when
        when(commonService.findByUserId(recipeRequestDto.getUserId())).thenReturn(user);
        when(commonService.findByIngredientsContaining(recipeRequestDto.getIngredients().get(0))).thenReturn(recipeList);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);

        Page<RecipeIngListResponseDto> response = recommendService.getIngredientsRecommendList(recipeRequestDto);

        // then
        assertNotNull(response);
    }

    @Test
    void 북마크추천아이디목록조회() throws JsonProcessingException {
        // given
        apiUrl = String.format("%s/bookmark/recommend?recipe_id=%d&page=%d", FLASK_API_URL, RECIPE_ID, PAGE);

        String responseJson = objectMapper.writeValueAsString(recipeIdList);

        // when
        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(responseJson);

        List<Long> response = recommendService.getRecommendBookmarkId(RECIPE_ID, PAGE);

        // then
        assertNotNull(response);
        assertEquals(response, Arrays.asList(recipeIdList));
    }


    @Test
    void 평점추천목록아이디조회() throws JsonProcessingException {
        // given
        apiUrl = String.format("%s/rating/recommend?user_id=%d&page=%d&bookmark_list=2&bookmark_list=3", FLASK_API_URL, USER_ID, PAGE);

        Object[] resultList = {bookmarkIdList};
        String responseJson = objectMapper.writeValueAsString(resultList);

        // when
        when(restTemplate.getForObject(apiUrl, String.class)).thenReturn(responseJson);

        List<Long> result = recommendService.getRecommendRatingId(USER_ID, PAGE, bookmarkIdList);

        // then
        assertNotNull(result);
        assertEquals(Arrays.asList((Long[]) resultList[1]), result);
    }

    @Test
    void 북마크및평점레시피목록조회() {
        // given
        recommendRequestDto = new RecommendRequestDto(USER_ID, recipeIdList);

        // when
        when(commonService.findByUserId(USER_ID)).thenReturn(user);
        when(commonService.findByRecipeIdIn(recipeIdList)).thenReturn(recipeList);
        when(commonService.isBookmarked(user, recipe)).thenReturn(true);

        List<RecipeListResponseDto> response = recommendService.getBookmarkOrRatingRecommendList(recommendRequestDto);

        // then
        assertNotNull(response);
        assertEquals(recipeList.size(), response.size());
    }
}