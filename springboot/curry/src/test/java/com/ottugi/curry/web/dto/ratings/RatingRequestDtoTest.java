package com.ottugi.curry.web.dto.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsTest;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RatingRequestDtoTest {
    private static final Map<Long, Double> ratingMap = new HashMap<>();

    public static RatingRequestDto initRatingRequestDto(Ratings ratings) {
        ratingMap.put(ratings.getRecipeId(), 3.0);
        return RatingRequestDto.builder()
                .userId(ratings.getUserId())
                .newUserRatingsDic(ratingMap)
                .build();
    }

    private Ratings ratings;

    @BeforeEach
    public void setUp() {
        User user = UserTest.initUser();
        Recipe recipe = RecipeTest.initRecipe();

        ratings = RatingsTest.initRatings(user, recipe);
    }

    @Test
    @DisplayName("RatingRequestDto 생성 테스트")
    void testRatingRequestDto() {
        RatingRequestDto ratingRequestDto = initRatingRequestDto(ratings);

        assertEquals(ratings.getUserId(), ratingRequestDto.getUserId());
        assertEquals(ratingMap, ratingRequestDto.getNewUserRatingsDic());
    }
}