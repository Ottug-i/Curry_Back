package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RatingRequestDto;
import com.ottugi.curry.web.dto.recommend.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecommendService {
    List<RecommendListResponseDto> getRandomRecipe();
    RatingResponseDto getUserRating(Long userId, Long recipeId) throws JsonProcessingException;
    Boolean updateUserRating(RatingRequestDto ratingRequestDto);
    Boolean deleteUserRating(Long userId, Long recipeId);
    Page<RecipeIngListResponseDto> getIngredientsRecommendList(RecipeRequestDto recipeRequestDto);
    List<Long> getRecommendBookmarkId(Long recipeId, int page) throws JsonProcessingException;
    List<Long> getRecommendRatingId(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException;
    List<RecipeListResponseDto> getBookmarkOrRatingRecommendList(RecommendRequestDto recommendRequestDto);
}
