package com.ottugi.curry.service.ratings;

import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RatingsService {
    @Transactional(readOnly = true)
    List<RecommendListResponseDto> findRandomRecipeListForResearch();

    @Transactional(readOnly = true)
    RatingResponseDto findUserRating(Long userId, Long recipeId);

    Boolean addOrUpdateUserRating(RatingRequestDto requestDto);

    Boolean removeUserRating(Long userId, Long recipeId);
}
