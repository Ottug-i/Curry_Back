package com.ottugi.curry.web.dto.ratings;

import com.ottugi.curry.domain.ratings.Ratings;
import org.junit.jupiter.api.Test;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RatingResponseDtoTest {

    private Ratings ratings;

    @Test
    void 평점_응답_Dto_롬복() {
        // given
        ratings = Ratings.builder()
                .id(RATING_ID)
                .userId(USER_ID)
                .recipeId(RECIPE_ID)
                .rating(RATING)
                .build();

        // when
        RatingResponseDto ratingResponseDto = new RatingResponseDto(ratings);

        // then
        assertEquals(ratings.getUserId(), ratingResponseDto.getUserId());
        assertEquals(ratings.getRecipeId(), ratingResponseDto.getRecipeId());
        assertEquals(ratings.getRating(), ratingResponseDto.getRating());
    }
}