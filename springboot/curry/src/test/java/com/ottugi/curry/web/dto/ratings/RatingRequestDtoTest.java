package com.ottugi.curry.web.dto.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.ratings.Ratings;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RatingRequestDtoTest {
    private Ratings ratings;
    private final Map<Long, Double> ratingMap = new HashMap<>();

    @BeforeEach
    public void setUp() {
        ratings = TestObjectFactory.initRatings();
        ratingMap.put(ratings.getRecipeId(), 3.0);
    }

    @Test
    @DisplayName("RatingRequestDto 생성 테스트")
    void testRatingRequestDto() {
        RatingRequestDto ratingRequestDto = RatingRequestDto.builder()
                .userId(ratings.getUserId())
                .newUserRatingsDic(ratingMap)
                .build();

        assertEquals(ratings.getUserId(), ratingRequestDto.getUserId());
        assertEquals(ratingMap, ratingRequestDto.getNewUserRatingsDic());
    }
}