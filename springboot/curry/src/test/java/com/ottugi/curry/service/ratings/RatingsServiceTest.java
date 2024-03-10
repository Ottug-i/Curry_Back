package com.ottugi.curry.service.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsRepository;
import com.ottugi.curry.domain.ratings.RatingsTest;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import com.ottugi.curry.web.dto.ratings.RatingRandomRecipeListResponseDto;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingRequestDtoTest;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RatingsServiceTest {
    private Ratings ratings;
    private Recipe recipe;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RatingsRepository ratingsRepository;
    @InjectMocks
    private RatingsServiceImpl ratingsService;

    @BeforeEach
    public void setUp() {
        User user = UserTest.initUser();
        recipe = RecipeTest.initRecipe();
        ratings = RatingsTest.initRatings(user, recipe);
    }

    @Test
    @DisplayName("초기 평점 수집을 위한 랜덤 레시피 목록 조회 테스트")
    void testFindRandomRecipeListForResearch() {
        when(recipeRepository.count()).thenReturn(10L);
        when(recipeRepository.findByIdIn(anyList())).thenReturn(Collections.singletonList(recipe));

        List<RatingRandomRecipeListResponseDto> result = ratingsService.findRandomRecipeListForResearch();

        assertEquals(1, result.size());
        assertRatingRandomRecipeListResponseDto(result.get(0));

        verify(recipeRepository, times(1)).count();
        verify(recipeRepository, times(1)).findByIdIn(anyList());
    }

    @Test
    @DisplayName("회원에 따른 레시피 평점 조회 테스트")
    void testFindUserRating() {
        when(ratingsRepository.existsByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(true);
        when(ratingsRepository.findByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(ratings);

        RatingResponseDto result = ratingsService.findUserRating(ratings.getUserId(), ratings.getRecipeId());

        assertRatingResponseDto(result);

        verify(ratingsRepository, times(1)).existsByUserIdAndRecipeId(anyLong(), anyLong());
        verify(ratingsRepository, times(1)).findByUserIdAndRecipeId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("회원에 따른 레시피 평점 조회 불가 테스트")
    void testNotFindUserRating() {
        when(ratingsRepository.existsByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(false);

        RatingResponseDto result = ratingsService.findUserRating(ratings.getUserId(), ratings.getRecipeId());

        assertNull(result);

        verify(ratingsRepository, times(1)).existsByUserIdAndRecipeId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("회원에 따른 레시피 평점 추가 테스트")
    void testAddUserRating() {
        when(ratingsRepository.existsByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(false);
        when(ratingsRepository.save(any(Ratings.class))).thenReturn(ratings);

        RatingRequestDto ratingRequestDto = RatingRequestDtoTest.initRatingRequestDto(ratings);
        boolean result = ratingsService.addOrUpdateUserRating(ratingRequestDto);

        assertTrue(result);

        verify(ratingsRepository, times(1)).existsByUserIdAndRecipeId(anyLong(), anyLong());
        verify(ratingsRepository, times(1)).save(any(Ratings.class));
    }

    @Test
    @DisplayName("회원에 따른 레시피 평점 수정 테스트")
    void testUpdateUserRating() {
        when(ratingsRepository.existsByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(true);
        when(ratingsRepository.findByUserIdAndRecipeId(anyLong(), anyLong())).thenReturn(ratings);

        RatingRequestDto ratingRequestDto = RatingRequestDtoTest.initRatingRequestDto(ratings);
        boolean result = ratingsService.addOrUpdateUserRating(ratingRequestDto);

        assertTrue(result);

        verify(ratingsRepository, times(1)).existsByUserIdAndRecipeId(anyLong(), anyLong());
        verify(ratingsRepository, times(1)).findByUserIdAndRecipeId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("회원에 따른 레시피 평점 삭제 테스트")
    void testRemoveUserRating() {
        doNothing().when(ratingsRepository).deleteByUserIdAndRecipeId(anyLong(), anyLong());

        boolean result = ratingsService.removeUserRating(ratings.getUserId(), recipe.getRecipeId());

        assertTrue(result);

        verify(ratingsRepository, times(1)).deleteByUserIdAndRecipeId(anyLong(), anyLong());
    }

    private void assertRatingRandomRecipeListResponseDto(RatingRandomRecipeListResponseDto resultDto) {
        assertNotNull(resultDto);
        assertEquals(recipe.getRecipeId(), resultDto.getRecipeId());
        assertEquals(recipe.getName(), resultDto.getName());
        assertEquals(recipe.getThumbnail(), resultDto.getThumbnail());
        assertEquals(recipe.getTime().getTimeName(), resultDto.getTime());
        assertEquals(recipe.getDifficulty().getDifficultyName(), resultDto.getDifficulty());
        assertEquals(recipe.getComposition().getCompositionName(), resultDto.getComposition());
        assertEquals(recipe.getIngredients(), resultDto.getIngredients());
    }

    private void assertRatingResponseDto(RatingResponseDto resultDto) {
        assertNotNull(resultDto);
        assertEquals(ratings.getRecipeId(), resultDto.getRecipeId());
        assertEquals(ratings.getUserId(), resultDto.getUserId());
        assertEquals(ratings.getRating(), resultDto.getRating());
    }
}