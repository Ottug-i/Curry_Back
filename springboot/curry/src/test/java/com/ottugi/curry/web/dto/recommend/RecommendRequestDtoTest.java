package com.ottugi.curry.web.dto.recommend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecommendRequestDtoTest {
    public static RecommendRequestDto initRecommendRequestDto(User user, Recipe recipe) {
        return RecommendRequestDto.builder()
                .userId(user.getId())
                .recipeId(Collections.singletonList(recipe.getId()))
                .build();
    }

    private Recipe recipe;
    private User user;

    @BeforeEach
    public void setUp() {
        recipe = RecipeTest.initRecipe();
        user = UserTest.initUser();
    }

    @Test
    @DisplayName("RecommendRequestDto 생성 테스트")
    void testRecommendRequestDto() {
        RecommendRequestDto recommendRequestDto = initRecommendRequestDto(user, recipe);

        assertEquals(user.getId(), recommendRequestDto.getUserId());
        assertEquals(recipe.getId(), recommendRequestDto.getRecipeId().get(0));
    }
}