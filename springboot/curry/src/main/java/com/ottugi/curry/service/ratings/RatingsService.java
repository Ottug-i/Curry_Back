package com.ottugi.curry.service.ratings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;

import java.util.List;

public interface RatingsService {
    List<RecommendListResponseDto> getRandomRecipe();
    RatingResponseDto getUserRating(Long userId, Long recipeId) throws JsonProcessingException;
    Boolean updateUserRating(RatingRequestDto ratingRequestDto);
    Boolean deleteUserRating(Long userId, Long recipeId);
}
