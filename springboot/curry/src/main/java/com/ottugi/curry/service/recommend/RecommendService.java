package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recipe.RecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RecipeIngListResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RecommendService {
    @Transactional(readOnly = true)
    Page<RecipeIngListResponseDto> findRecipePageByIngredientsDetection(RecipeRequestDto recipeRequestDto);

    List<Long> findRecipeIdListByBookmarkRecommend(Long recipeId, int page) throws JsonProcessingException;

    List<Long> findRecipeIdListByRatingRecommend(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException;

    @Transactional(readOnly = true)
    List<RecipeListResponseDto> findBookmarkOrRatingRecommendList(RecommendRequestDto recommendRequestDto);
}
