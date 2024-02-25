package com.ottugi.curry.service.ratings;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import com.ottugi.curry.web.dto.recommend.RecommendListResponseDto;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingsServiceImpl implements RatingsService {
    private final Long RANDOM_MIN = 1L;
    private final Long RANDOM_MAX = 3616L;
    private final Integer BATCH_SIZE = 10;

    private final RatingsRepository ratingsRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public List<RecommendListResponseDto> findRandomRecipeListForResearch() {
        List<Long> selectedIdList = selectRandomNumbers();
        List<Recipe> recipeList = recipeRepository.findByIdIn(selectedIdList);
        return recipeList.stream().map(RecommendListResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public RatingResponseDto findUserRating(Long userId, Long recipeId) {
        if (existsByUserIdAndRecipeId(userId, recipeId)) {
            Ratings ratings = findByUserIdAndRecipeId(userId, recipeId);
            return new RatingResponseDto(ratings);
        }
        return null;
    }

    @Override
    public Boolean addOrUpdateUserRating(RatingRequestDto requestDto) {
        for (Long recipeId : requestDto.getNewUserRatingsDic().keySet()) {
            if (existsByUserIdAndRecipeId(requestDto.getUserId(), recipeId)) {
                updateUserRating(requestDto.getUserId(), recipeId, requestDto.getNewUserRatingsDic().get(recipeId));
            } else {
                createUserRating(requestDto.getUserId(), recipeId, requestDto.getNewUserRatingsDic().get(recipeId));
            }
        }
        return true;
    }

    @Override
    public Boolean deleteUserRating(Long userId, Long recipeId) {
        ratingsRepository.deleteByUserIdAndRecipeId(userId, recipeId);
        return true;
    }

    private List<Long> selectRandomNumbers() {
        return ThreadLocalRandom.current()
                .longs(RANDOM_MIN, RANDOM_MAX + 1)
                .distinct()
                .limit(BATCH_SIZE)
                .boxed()
                .collect(Collectors.toList());
    }

    private Boolean existsByUserIdAndRecipeId(Long userId, Long recipeId) {
        return ratingsRepository.existsByUserIdAndRecipeId(userId, recipeId);
    }

    private Ratings findByUserIdAndRecipeId(Long userId, Long recipeId) {
        return ratingsRepository.findByUserIdAndRecipeId(userId, recipeId);
    }

    private void createUserRating(Long userId, Long recipeId, Double rating) {
        Ratings newRatings = Ratings.builder()
                .userId(userId)
                .recipeId(recipeId)
                .rating(rating)
                .build();
        ratingsRepository.save(newRatings);
    }

    private void updateUserRating(Long userId, Long recipeId, Double rating) {
        Ratings existingRating = findByUserIdAndRecipeId(userId, recipeId);
        existingRating.updateRatings(rating);
    }
}
