package com.ottugi.curry.service.ratings;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsRepository;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeRepository;
import com.ottugi.curry.web.dto.ratings.RatingRandomRecipeListResponseDto;
import com.ottugi.curry.web.dto.ratings.RatingRequestDto;
import com.ottugi.curry.web.dto.ratings.RatingResponseDto;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingsServiceImpl implements RatingsService {
    private static final int RANDOM_SIZE = 10;

    private final RatingsRepository ratingsRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public List<RatingRandomRecipeListResponseDto> findRandomRecipeListForResearch() {
        List<Recipe> recipeList = selectRandomRecipeList();
        return recipeList
                .stream()
                .map(RatingRandomRecipeListResponseDto::new)
                .collect(Collectors.toList());
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
    public boolean addOrUpdateUserRating(RatingRequestDto requestDto) {
        requestDto.getNewUserRatingsDic().forEach((recipeId, rating) -> {
            if (existsByUserIdAndRecipeId(requestDto.getUserId(), recipeId)) {
                updateUserRating(requestDto.getUserId(), recipeId, rating);
            } else {
                createUserRating(requestDto.getUserId(), recipeId, rating);
            }
        });
        return true;
    }

    @Override
    public boolean removeUserRating(Long userId, Long recipeId) {
        ratingsRepository.deleteByUserIdAndRecipeId(userId, recipeId);
        return true;
    }

    private List<Recipe> selectRandomRecipeList() {
        List<Long> selectedRecipeIdList = selectRandomRecipeIdList();
        return recipeRepository.findByIdIn(selectedRecipeIdList);
    }

    private List<Long> selectRandomRecipeIdList() {
        long recipeCount = recipeRepository.count();
        return ThreadLocalRandom.current()
                .longs(1, recipeCount + 1)
                .distinct()
                .limit(RANDOM_SIZE)
                .boxed()
                .collect(Collectors.toList());
    }

    private boolean existsByUserIdAndRecipeId(Long userId, Long recipeId) {
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
