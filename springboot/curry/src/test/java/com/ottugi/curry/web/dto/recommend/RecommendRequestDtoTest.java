package com.ottugi.curry.web.dto.recommend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.user.User;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecommendRequestDtoTest {
    private Recipe recipe;
    private User user;

    @BeforeEach
    public void setUp() {
        recipe = TestObjectFactory.initRecipe();
        user = TestObjectFactory.initUser();
    }

    @Test
    @DisplayName("RecommendRequestDto 생성 테스트")
    void testRecommendRequestDto() {
        RecommendRequestDto recommendRequestDto = RecommendRequestDto.builder()
                .userId(user.getId())
                .recipeId(Collections.singletonList(recipe.getId()))
                .build();

        assertEquals(user.getId(), recommendRequestDto.getUserId());
        assertEquals(recipe.getRecipeId(), recommendRequestDto.getRecipeId().get(0));
    }
}