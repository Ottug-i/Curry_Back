package com.ottugi.curry.web.dto.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.ratings.Ratings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RatingResponseDtoTest {
    private Ratings ratings;

    @BeforeEach
    public void setUp() {
        ratings = TestObjectFactory.initRatings();
    }

    @Test
    @DisplayName("RatingResponseDto 생성 테스트")
    void testRatingResponseDto() {
        RatingResponseDto ratingResponseDto = new RatingResponseDto(ratings);
        
        assertEquals(ratings.getUserId(), ratingResponseDto.getUserId());
        assertEquals(ratings.getRecipeId(), ratingResponseDto.getRecipeId());
        assertEquals(ratings.getRating(), ratingResponseDto.getRating());
    }
}