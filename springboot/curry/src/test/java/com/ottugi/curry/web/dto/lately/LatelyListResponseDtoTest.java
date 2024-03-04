package com.ottugi.curry.web.dto.lately;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.domain.lately.Lately;
import com.ottugi.curry.domain.lately.LatelyTest;
import com.ottugi.curry.domain.recipe.RecipeTest;
import com.ottugi.curry.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LatelyListResponseDtoTest {
    public static LatelyListResponseDto initLatelyListResponseDto(Lately lately) {
        return new LatelyListResponseDto(lately);
    }

    private Lately lately;

    @BeforeEach
    public void setUp() {
        lately = LatelyTest.initLately();
        lately.setUser(UserTest.initUser());
        lately.setRecipe(RecipeTest.initRecipe());
    }

    @Test
    @DisplayName("LatelyListResponseDto 생성 테스트")
    void testLatelyListResponseDto() {
        LatelyListResponseDto latelyListResponseDto = initLatelyListResponseDto(lately);

        assertEquals(lately.getRecipeId().getRecipeId(), latelyListResponseDto.getRecipeId());
        assertEquals(lately.getRecipeId().getName(), latelyListResponseDto.getName());
        assertEquals(lately.getRecipeId().getThumbnail(), latelyListResponseDto.getThumbnail());
    }
}