package com.ottugi.curry.service.ratings;

import static com.ottugi.curry.TestConstants.COMPOSITION;
import static com.ottugi.curry.TestConstants.DIFFICULTY;
import static com.ottugi.curry.TestConstants.GENRE;
import static com.ottugi.curry.TestConstants.ID;
import static com.ottugi.curry.TestConstants.INGREDIENTS;
import static com.ottugi.curry.TestConstants.NAME;
import static com.ottugi.curry.TestConstants.ORDERS;
import static com.ottugi.curry.TestConstants.PHOTO;
import static com.ottugi.curry.TestConstants.RATING;
import static com.ottugi.curry.TestConstants.RATING_ID;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.SERVINGS;
import static com.ottugi.curry.TestConstants.THUMBNAIL;
import static com.ottugi.curry.TestConstants.TIME;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RatingsServiceTest {

    private Ratings ratings;
    private Recipe recipe;

    private final List<Long> idList = new ArrayList<>();
    private final List<Recipe> recipeList = new ArrayList<>();

    @Mock
    private RatingsRepository ratingsRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CommonService commonService;

    @InjectMocks
    private RatingsServiceImpl ratingsService;

    private final Map<Long, Double> newUserRatingsDic = new HashMap<>();

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
        ratingsRepository.deleteAll();
    }

    @Test
    void 초기_평점을_위한_랜덤_레시피_목록_조회() {
        // given
        when(commonService.findByIdIn(anyList())).thenReturn(recipeList);

        // when
        List<RecommendListResponseDto> testRecommendListResponseDtoList = ratingsService.findRandomRecipeListForResearch();

        // then
        assertNotNull(testRecommendListResponseDtoList);
        assertEquals(idList.size(), testRecommendListResponseDtoList.size());
    }

    @Test
    void 레시피_평점_조회() {
        // given
        when(ratingsRepository.findByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(ratings);

        // when
        RatingResponseDto testRatingResponseDto = ratingsService.findUserRating(USER_ID, recipe.getRecipeId());

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
        Boolean testResponse = ratingsService.removeUserRating(USER_ID, recipe.getRecipeId());

        // then
        assertTrue(testResponse);
    }

}