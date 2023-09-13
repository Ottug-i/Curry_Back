package com.ottugi.curry.domain.ratings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RatingsTest {

    private Ratings ratings;

    @BeforeEach
    public void setUp() {
        // given
        ratings = new Ratings(RATING_ID, USER_ID, RECIPE_ID, RATING);
    }

    @Test
    void 평점_추가() {
        // when, then
        assertEquals(USER_ID, ratings.getUserId());
        assertEquals(RECIPE_ID, ratings.getRecipeId());
        assertEquals(RATING, ratings.getRating());
    }

    @Test
    void 평점_수정() {
        // when
        ratings.updateRatings(NEW_RATING);

        // then
        assertEquals(NEW_RATING, ratings.getRating());
    }
}