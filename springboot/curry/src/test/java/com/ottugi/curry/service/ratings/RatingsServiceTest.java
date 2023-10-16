package com.ottugi.curry.service.ratings;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RatingsServiceTest {

    private Ratings ratings;
    private Recipe recipe;

    private List<Long> idList = new ArrayList<>();
    private List<Recipe> recipeList = new ArrayList<>();

    @Mock
    private RatingsRepository ratingsRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CommonService commonService;

    @InjectMocks
    private RatingsServiceImpl ratingsService;

    private Map<Long, Double> newUserRatingsDic = new HashMap<>();

    @BeforeEach
    public void setUp() {
        // given
        recipe = new Recipe(ID, RECIPE_ID, NAME, THUMBNAIL, TIME, DIFFICULTY, COMPOSITION, INGREDIENTS, SERVINGS, ORDERS, PHOTO, GENRE);
        ratings = new Ratings(RATING_ID, USER_ID, RECIPE_ID, RATING);

        // when
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(ratingsRepository.save(any(Ratings.class))).thenReturn(ratings);

        newUserRatingsDic.put(6847060L, 3.0);
    }

    @AfterEach
    public void clean() {
        // clean
        ratingsRepository.deleteAll();;
    }

    @Test
    void 초기_평점을_위한_랜덤_레시피_목록_조회() {
        // given
        when(commonService.findByIdIn(anyList())).thenReturn(recipeList);

        // when
        List<RecommendListResponseDto> testRecommendListResponseDtoList = ratingsService.getRandomRecipe();

        // then
        assertNotNull(testRecommendListResponseDtoList);
        assertEquals(idList.size(), testRecommendListResponseDtoList.size());
    }

    @Test
    void 레시피_평점_조회() {
        // given
        when(ratingsRepository.findByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(ratings);

        // when
        RatingResponseDto testRatingResponseDto = ratingsService.getUserRating(USER_ID, recipe.getRecipeId());

        // then
        assertNotNull(testRatingResponseDto);
        assertEquals(USER_ID, testRatingResponseDto.getUserId());
        assertEquals(recipe.getRecipeId(), testRatingResponseDto.getRecipeId());
    }

    @Test
    void 레시피_평점_업데이트() {
        // given
        when(ratingsRepository.findByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(ratings);

        // when
        RatingRequestDto ratingRequestDto = new RatingRequestDto(USER_ID, newUserRatingsDic);
        Boolean testResponse = ratingsService.updateUserRating(ratingRequestDto);

        // then
        assertTrue(testResponse);
    }

    @Test
    void 레시피_평점_삭제() {
        // given
        when(ratingsRepository.findByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(ratings);

        // when
        Boolean testResponse = ratingsService.deleteUserRating(USER_ID, recipe.getRecipeId());

        // then
        assertTrue(testResponse);
    }

}