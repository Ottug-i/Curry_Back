package com.ottugi.curry.domain.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RatingsTest {
    public static final Long RATING_ID = 1L;
    public static final Double RATING = 5.0;
    public static final Double NEW_RATING = 4.0;

    public static Ratings initRatings(User user, Recipe recipe) {
        return Ratings.builder()
                .id(RATING_ID)
                .userId(user.getId())
                .recipeId(recipe.getRecipeId())
                .rating(RATING)
                .build();
    }

    private Ratings ratings;
    private User user;
    private Recipe recipe;

    @BeforeEach
    public void setUp() {
        user = UserTest.initUser();
        recipe = RecipeTest.initRecipe();
        ratings = initRatings(user, recipe);
    }

    @Test
    @DisplayName("평점 추가 테스트")
    void testRatings() {
        assertNotNull(ratings);
        assertEquals(RATING_ID, ratings.getId());
        assertEquals(user.getId(), ratings.getUserId());
        assertEquals(recipe.getRecipeId(), ratings.getRecipeId());
        assertEquals(RATING, ratings.getRating());
    }

    @Test
    @DisplayName("평점 수정 테스트")
    void testUpdateRatings() {
        ratings.updateRatings(NEW_RATING);

        assertEquals(NEW_RATING, ratings.getRating());
    }
}