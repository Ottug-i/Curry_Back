package com.ottugi.curry.web.dto.recommend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.ValidatorUtil;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecommendRequestDtoTest {
    private final ValidatorUtil<RecommendRequestDto> validatorUtil = new ValidatorUtil<>();

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

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        RecommendRequestDto recommendRequestDto = new RecommendRequestDto();

        assertNotNull(recommendRequestDto);
        assertNull(recommendRequestDto.getUserId());
        assertNull(recommendRequestDto.getRecipeId());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        RecommendRequestDto recommendRequestDto = RecommendRequestDto.builder()
                .userId(null)
                .recipeId(Collections.singletonList(recipe.getId()))
                .build();

        validatorUtil.validate(recommendRequestDto);
    }

    @Test
    @DisplayName("인식된 재료 유효성 검증 테스트")
    void ingredients_validation() {
        RecommendRequestDto recommendRequestDto = RecommendRequestDto.builder()
                .userId(user.getId())
                .recipeId(Collections.emptyList())
                .build();

        validatorUtil.validate(recommendRequestDto);
    }
}