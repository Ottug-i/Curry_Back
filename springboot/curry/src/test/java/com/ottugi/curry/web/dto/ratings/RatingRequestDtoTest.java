package com.ottugi.curry.web.dto.ratings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ottugi.curry.ValidatorUtil;
import com.ottugi.curry.domain.ratings.Ratings;
import com.ottugi.curry.domain.ratings.RatingsTest;
import com.ottugi.curry.domain.recipe.Recipe;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.User;
import com.ottugi.curry.domain.user.UserTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RatingRequestDtoTest {
    private final ValidatorUtil<RatingRequestDto> validatorUtil = new ValidatorUtil<>();
    private static final Map<Long, Double> ratingMap = new HashMap<>();

    public static RatingRequestDto initRatingRequestDto(Ratings ratings) {
        ratingMap.put(ratings.getRecipeId(), 3.0);
        return RatingRequestDto.builder()
                .userId(ratings.getUserId())
                .newUserRatingsDic(ratingMap)
                .build();
    }

    private Ratings ratings;

    @BeforeEach
    public void setUp() {
        User user = UserTest.initUser();
        Recipe recipe = RecipeTest.initRecipe();

        ratings = RatingsTest.initRatings(user, recipe);
    }

    @Test
    @DisplayName("RatingRequestDto 생성 테스트")
    void testRatingRequestDto() {
        RatingRequestDto ratingRequestDto = initRatingRequestDto(ratings);

        assertEquals(ratings.getUserId(), ratingRequestDto.getUserId());
        assertEquals(ratingMap, ratingRequestDto.getNewUserRatingsDic());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        RatingRequestDto ratingRequestDto = new RatingRequestDto();

        assertNotNull(ratingRequestDto);
        assertNull(ratingRequestDto.getUserId());
        assertNull(ratingRequestDto.getNewUserRatingsDic());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        ratingMap.put(ratings.getRecipeId(), 3.0);
        RatingRequestDto ratingRequestDto = RatingRequestDto.builder()
                .userId(null)
                .newUserRatingsDic(ratingMap)
                .build();

        validatorUtil.validate(ratingRequestDto);
    }

    @Test
    @DisplayName("레시피에 따른 평점 정보 유효성 검증 테스트")
    void newUserRatingsDic_validation() {
        RatingRequestDto ratingRequestDto = RatingRequestDto.builder()
                .userId(ratings.getUserId())
                .newUserRatingsDic(ratingMap)
                .build();

        validatorUtil.validate(ratingRequestDto);
    }
}