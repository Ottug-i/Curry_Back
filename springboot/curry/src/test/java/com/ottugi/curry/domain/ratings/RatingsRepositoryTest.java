package com.ottugi.curry.domain.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RatingsRepositoryTest {
    private Ratings ratings;

    @Autowired
    private RatingsRepository ratingsRepository;

    @BeforeEach
    public void setUp() {
        ratings = RatingsTest.initRatings(UserTest.initUser(), RecipeTest.initRecipe());
        ratings = ratingsRepository.save(ratings);
    }

    @AfterEach
    public void clean() {
        ratingsRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 평점 조회 테스트")
    void testFindByUserIdAndRecipeId() {
        Ratings foundRatings = ratingsRepository.findByUserIdAndRecipeId(ratings.getUserId(), ratings.getRecipeId());

        assertEquals(ratings.getUserId(), foundRatings.getUserId());
        assertEquals(ratings.getRecipeId(), foundRatings.getRecipeId());
        assertEquals(ratings.getRating(), foundRatings.getRating());
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 평점 조회 테스트")
    void testExistsByUserIdAndRecipeId() {
        Ratings foundRatings = ratingsRepository.findByUserIdAndRecipeId(ratings.getUserId(), ratings.getRecipeId());

        assertEquals(ratings.getUserId(), foundRatings.getUserId());
        assertEquals(ratings.getRecipeId(), foundRatings.getRecipeId());
    }

    @Test
    @DisplayName("회원 아이디와 레시피 아이디로 평점 삭제 테스트")
    void testDeleteByUserIdAndRecipeId() {
        ratingsRepository.deleteByUserIdAndRecipeId(ratings.getUserId(), ratings.getRecipeId());
        boolean existBookmark = ratingsRepository.existsByUserIdAndRecipeId(ratings.getUserId(), ratings.getRecipeId());

        assertFalse(existBookmark);
    }
}