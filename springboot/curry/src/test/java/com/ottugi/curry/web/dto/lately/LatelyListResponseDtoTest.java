package com.ottugi.curry.web.dto.lately;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ottugi.curry.TestObjectFactory;
import com.ottugi.curry.domain.lately.Lately;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LatelyListResponseDtoTest {
    private Lately lately;

    @BeforeEach
    public void setUp() {
        lately = TestObjectFactory.initLately();
        lately.setUser(TestObjectFactory.initUser());
        lately.setRecipe(TestObjectFactory.initRecipe());
    }

    @Test
    @DisplayName("LatelyListResponseDto 생성 테스트")
    void testLatelyListResponseDto() {
        LatelyListResponseDto latelyListResponseDto = new LatelyListResponseDto(lately);
        
        assertEquals(lately.getRecipeId().getRecipeId(), latelyListResponseDto.getRecipeId());
        assertEquals(lately.getRecipeId().getName(), latelyListResponseDto.getName());
        assertEquals(lately.getRecipeId().getThumbnail(), latelyListResponseDto.getThumbnail());
    }
}