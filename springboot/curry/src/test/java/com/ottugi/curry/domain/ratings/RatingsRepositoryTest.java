package com.ottugi.curry.domain.ratings;

import static com.ottugi.curry.TestConstants.RECIPE_ID;
import static com.ottugi.curry.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ottugi.curry.TestObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RatingsRepositoryTest {
    private Ratings ratings;

    @Autowired
    private RatingsRepository ratingsRepository;

    @BeforeEach
    public void setUp() {
        ratings = TestObjectFactory.initRatings();
        ratings = ratingsRepository.save(ratings);
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 평점 조회 테스트")
    void testFindByUserIdAndRecipeId() {
        Ratings foundRatings = ratingsRepository.findByUserIdAndRecipeId(USER_ID, RECIPE_ID);

        assertEquals(ratings.getUserId(), foundRatings.getUserId());
        assertEquals(ratings.getRecipeId(), foundRatings.getRecipeId());
        assertEquals(ratings.getRating(), foundRatings.getRating());
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 평점 조회 테스트")
    void testExistsByUserIdAndRecipeId() {
        Ratings foundRatings = ratingsRepository.findByUserIdAndRecipeId(USER_ID, RECIPE_ID);

        assertEquals(USER_ID, foundRatings.getUserId());
        assertEquals(RECIPE_ID, foundRatings.getRecipeId());
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 평점 삭제 테스트")
    void testDeleteByUserIdAndRecipeId() {
        ratingsRepository.deleteByUserIdAndRecipeId(USER_ID, RECIPE_ID);
        boolean existBookmark = ratingsRepository.existsByUserIdAndRecipeId(USER_ID, RECIPE_ID);

        assertFalse(existBookmark);
    }
}