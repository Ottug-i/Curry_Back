package com.ottugi.curry.web.dto.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsTest;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RatingResponseDtoTest {
    public static RatingResponseDto initRatingResponseDto(Ratings ratings) {
        return new RatingResponseDto(ratings);
    }

    private Ratings ratings;

    @BeforeEach
    public void setUp() {
        User user = UserTest.initUser();
        Recipe recipe = RecipeTest.initRecipe();

        ratings = RatingsTest.initRatings(user, recipe);
    }

    @Test
    @DisplayName("RatingResponseDto 생성 테스트")
    void testRatingResponseDto() {
        RatingResponseDto ratingResponseDto = initRatingResponseDto(ratings);

        assertEquals(ratings.getUserId(), ratingResponseDto.getUserId());
        assertEquals(ratings.getRecipeId(), ratingResponseDto.getRecipeId());
        assertEquals(ratings.getRating(), ratingResponseDto.getRating());
    }
}