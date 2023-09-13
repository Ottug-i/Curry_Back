package com.ottugi.curry.service.ratings;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.service.CommonService;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingsServiceImpl implements RatingsService {

    private final RatingsRepository ratingsRepository;
    private final CommonService commonService;

    // 랜덤 레시피 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<RecommendListResponseDto> getRandomRecipe() {
        long min = 1L;
        long max = 3616L;
        int batchSize = 10;

        List<Long> selectedIdList = ThreadLocalRandom.current().longs(min, max + 1).distinct().limit(batchSize).boxed().collect(Collectors.toList());
        List<Recipe> recipeList = commonService.findByIdIn(selectedIdList);

        return recipeList.stream().map(recipe -> new RecommendListResponseDto(recipe)).collect(Collectors.toList());
    }

    // 유저 레시피 평점 조회
    @Override
    @Transactional(readOnly = true)
    public RatingResponseDto getUserRating(Long userId, Long recipeId) {
        Ratings ratings = ratingsRepository.findByUserIdAndRecipeId(userId, recipeId);
        if (ratings != null) {
            return new RatingResponseDto(ratings);
        }
        else {
            return null;
        }
    }

    // 유저 레시피 평점 추가 또는 수정
    @Override
    @Transactional
    public Boolean updateUserRating(RatingRequestDto ratingRequestDto) {
        for (Long recipeId : ratingRequestDto.getNewUserRatingsDic().keySet()) {
            Ratings rating = ratingsRepository.findByUserIdAndRecipeId(ratingRequestDto.getUserId(), recipeId);
            if (rating == null) {
                Ratings newRatings = Ratings.builder().userId(ratingRequestDto.getUserId()).recipeId(recipeId).rating(ratingRequestDto.getNewUserRatingsDic().get(recipeId)).build();
                ratingsRepository.save(newRatings);
            }
            else {
                rating.updateRatings(ratingRequestDto.getNewUserRatingsDic().get(recipeId));
            }
        }
        return true;
    }

    // 유저 레시피 평점 삭제
    @Override
    @Transactional
    public Boolean deleteUserRating(Long userId, Long recipeId) {
        Ratings ratings = ratingsRepository.findByUserIdAndRecipeId(userId, recipeId);
        if (ratings != null) {
            ratingsRepository.delete(ratings);
            return true;
        }
        else {
            return false;
        }
    }
}
