package com.ottugi.curry.domain.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeTest {
    @Test
    @DisplayName("올바른 레시피 시간 열거형 값 테스트")
    void testOfValidTime() {
        String validTime = Time.FIFTEEN_MINUTES.getTimeName();

        Time time = Time.ofTime(validTime);

        assertNotNull(time);
        assertEquals(Time.FIFTEEN_MINUTES, time);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 시간 열거형 값 테스트")
    void testOfInvalidTime() {
        String invalidTime = "25분 이내";

        Time difficulty = Time.ofTime(invalidTime);

        assertNull(difficulty);
    }

    @Test
    @DisplayName("동일한 시간 일치 테스트")
    void testIsTimeMatching() {
        Recipe recipe = RecipeTest.initRecipe();
        String time = Time.SIXTY_MINUTES.getTimeName();

        Boolean result = Time.isTimeMatching(recipe, time);

        assertTrue(result);
    }
}