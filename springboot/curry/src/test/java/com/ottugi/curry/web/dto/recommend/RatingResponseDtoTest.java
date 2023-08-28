package com.ottugi.curry.web.dto.recommend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RatingResponseDtoTest {

    private List<Double> ratingInfo = Arrays.asList(6846342.0, 1.0, 4.0);

    @Test
    void 평점_응답_Dto_롬복() {
        // when
        RatingResponseDto ratingResponseDto = new RatingResponseDto(ratingInfo);

        // then
        assertEquals(ratingInfo.get(0), ratingResponseDto.getRecipeId());
        assertEquals(ratingInfo.get(1), ratingResponseDto.getUserId());
        assertEquals(ratingInfo.get(2), ratingResponseDto.getRating());
    }
}