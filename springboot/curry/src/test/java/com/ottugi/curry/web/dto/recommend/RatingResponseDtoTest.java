package com.ottugi.curry.web.dto.recommend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RatingResponseDtoTest {

    private List<Double> ratingInfo = Arrays.asList(6846342.0, 1.0, 4.0);

    @Test
    void RatingResponseDto_롬복() {
        // when
        RatingResponseDto ratingResponseDto = new RatingResponseDto(ratingInfo);

        // then
        assertEquals(ratingResponseDto.getRecipeId(), ratingInfo.get(0));
        assertEquals(ratingResponseDto.getUserId(), ratingInfo.get(1));
        assertEquals(ratingResponseDto.getRating(), ratingInfo.get(2));
    }
}