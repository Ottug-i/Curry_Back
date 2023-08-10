package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.web.dto.recommend.RatingRequestDto;
import com.ottugi.curry.web.dto.recommend.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;

import java.util.List;

public interface RecommendService {

    List<RecommendListResponseDto> getRandomRecipe();
    List<Long> getRecommendBookmark(Long recipeId, int page) throws JsonProcessingException;
    RatingResponseDto getUserRating(Long userId, Long recipeId) throws JsonProcessingException;
    Boolean updateUserRating(RatingRequestDto ratingRequestDto);
    List<Long> getRecommendRating(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException;
}
