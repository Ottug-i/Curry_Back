package com.ottugi.curry.web.dto.recommend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RecommendRequestDtoTest {

    private List<Long> recipeIdList = Arrays.asList(RECIPE_ID);

    @Test
    void RecommendRequestDto_롬복() {
        // when
        RecommendRequestDto recommendRequestDto = new RecommendRequestDto(USER_ID, recipeIdList);

        // then
        assertEquals(USER_ID, recommendRequestDto.getUserId());
        assertEquals(RECIPE_ID, recommendRequestDto.getRecipeId().get(0));
    }
}