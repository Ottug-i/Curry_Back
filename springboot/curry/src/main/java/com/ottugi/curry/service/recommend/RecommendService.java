package com.ottugi.curry.service.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ottugi.curry.web.dto.recipe.RecipeListResponseDto;
import com.ottugi.curry.web.dto.recommend.IngredientDetectionRecipeListResponseDto;
import com.ottugi.curry.web.dto.recommend.IngredientDetectionRecipeRequestDto;
import com.ottugi.curry.web.dto.recommend.RecommendRecipeRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RecommendService {
    /**
     * 재료 객체 인식에 따라 검출된 재료에 따른 레시피 추천 목록 페이지를 조회한다.
     *
     * @param requestDto (검출된 재료 정보를 담은 DTO)
     * @return Page<IngredientDetectionRecipeListResponseDto> (검출된 재료에 따른 레시피 목록 정보를 담은 DTO 페이지)
     */
    @Transactional(readOnly = true)
    Page<IngredientDetectionRecipeListResponseDto> findRecipePageByIngredientsDetection(IngredientDetectionRecipeRequestDto requestDto);

    /**
     * 북마크 추천 시스템에 따른 레시피 아이디 목록을 조회한다. 북마크한 레시피와 비슷한 레시피를 추천한다.
     *
     * @param recipeId (북마크한 레시피 아이디)
     * @param page     (페이지 번호)
     * @return List<Long> (북마크 추천 시스템에 따른 레시피 아이디 목록)
     */
    List<Long> findRecipeIdListByBookmarkRecommend(Long recipeId, int page) throws JsonProcessingException;

    /**
     * 평점 추천 시스템에 따른 레시피 아이디 목록을 조회한다. 사용자의 레시피 평점 정보를 기반으로 레시피를 추천한다.
     *
     * @param userId       (회원 아이디)
     * @param page         (페이지 번호)
     * @param bookmarkList (북마크 아이디 목롥)
     * @return List<Long> (평점 추천 시스템에 따른 레시피 아이디 목록)
     */
    List<Long> findRecipeIdListByRatingRecommend(Long userId, int page, Long[] bookmarkList) throws JsonProcessingException;

    /**
     * 북마크 추천 시스템과 평점 추천 시스템에 따른 레시피 목록을 조회한다.
     *
     * @param requestDto (추천 레시피 정보를 담은 DTO)
     * @return List<RecipeListResponseDto> (추천 레시피에 따른 레시피 정보를 담은 DTO 리스트)
     */
    @Transactional(readOnly = true)
    List<RecipeListResponseDto> findBookmarkOrRatingRecommendList(RecommendRecipeRequestDto requestDto);
}
