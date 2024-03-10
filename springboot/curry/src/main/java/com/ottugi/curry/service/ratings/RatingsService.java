package com.ottugi.curry.service.ratings;

import com.ottugi.curry.web.dto.ratings.RatingRandomRecipeListResponseDto;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RatingsService {
    /**
     * 레시피 평점 추천 시스템의 레시피 초기 평점 수집을 위한 랜덤 레시피 10개를 조회한다.
     *
     * @return List<RatingRandomRecipeListResponseDto> (레시피 초기 평점 수집을 위한 랜덤 레시피 정보를 담은 DTO 리스트)
     */
    @Transactional(readOnly = true)
    List<RatingRandomRecipeListResponseDto> findRandomRecipeListForResearch();

    /**
     * 회원 아이디와 레시피 아이디에 따른 레시피 평점 정보를 조회한다.
     *
     * @param userId   (회원 아이디)
     * @param recipeId (레시피 아이디)
     * @return RatingResponseDto (평점 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    RatingResponseDto findUserRating(Long userId, Long recipeId);

    /**
     * 레시피 평점을 추가 또는 수정한다.
     *
     * @param requestDto (평점 추가 또는 수정 정보 DTO)
     * @return boolean (회원 평점을 추가 또는 수정 후 true)
     */
    boolean addOrUpdateUserRating(RatingRequestDto requestDto);

    /**
     * 레시피 평점을 삭제한다.
     *
     * @param userId   (회원 아이디)
     * @param recipeId (레시피 아이디)
     * @return boolean (회원 평점을 삭제 후 true)
     */
    boolean removeUserRating(Long userId, Long recipeId);
}
