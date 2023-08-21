package com.ottugi.curry.web.dto.recommend;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RatingRequestDtoTest {

    private Map<Long, Double> newUserRatingsDic = new HashMap<>();

    @Test
    void RatingRequestDto_롬복() {
        // given
        newUserRatingsDic.put(6847060L, 3.0);

        // when
        RatingRequestDto ratingRequestDto = new RatingRequestDto(USER_ID, newUserRatingsDic);

        // then
        assertEquals(USER_ID, ratingRequestDto.getUserId());
        assertEquals(newUserRatingsDic, ratingRequestDto.getNewUserRatingsDic());
    }
}