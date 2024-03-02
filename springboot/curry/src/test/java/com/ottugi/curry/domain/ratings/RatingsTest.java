package com.ottugi.curry.domain.ratings;

import static com.ottugi.curry.TestConstants.NEW_RATING;
import static com.ottugi.curry.TestConstants.RATING;
import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RatingsTest {
    private Ratings ratings;

    @BeforeEach
    public void setUp() {
        ratings = TestObjectFactory.initRatings();
    }

    @Test
    @DisplayName("평점 추가 테스트")
    void testRatings() {
        assertNotNull(ratings);
        assertEquals(USER_ID, ratings.getUserId());
        assertEquals(RECIPE_ID, ratings.getRecipeId());
        assertEquals(RATING, ratings.getRating());
    }

    @Test
    @DisplayName("평점 수정 테스트")
    void testUpdateRatings() {
        ratings.updateRatings(NEW_RATING);

        assertEquals(NEW_RATING, ratings.getRating());
    }
}