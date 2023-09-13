package com.ottugi.curry.domain.ratings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ottugi.curry.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RatingsRepositoryTest {

    private Ratings ratings;

    private RatingsRepository ratingsRepository;

    private Ratings testRatings;

    @Autowired
    RatingsRepositoryTest(RatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    @BeforeEach
    public void setUp() {
        // given
        ratings = new Ratings(RATING_ID, USER_ID, RATING_ID, RATING);
        ratings = ratingsRepository.save(ratings);
    }

    @Test
    void 유저아이디와_레시피아이디로_평점_조회() {
        // when
        testRatings = ratingsRepository.findByUserIdAndRecipeId(USER_ID, RECIPE_ID);

        // then
        assertEquals(ratings.getUserId(), testRatings.getUserId());
        assertEquals(ratings.getRecipeId(), testRatings.getRecipeId());
        assertEquals(ratings.getRating(), testRatings.getRating());
    }
}