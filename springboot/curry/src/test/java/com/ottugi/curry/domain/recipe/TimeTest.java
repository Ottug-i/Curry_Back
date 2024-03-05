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
        Recipe recipe1 = RecipeTest.initRecipe();
        String time1 = Time.SIXTY_MINUTES.getTimeName();

        Recipe recipe2 = RecipeTest.initRecipe();
        recipe2.setTime(Time.TWO_HOURS);
        String time2 = Time.TWO_HOURS.getTimeName();

        Boolean result1 = Time.isTimeMatching(recipe1, time1);
        Boolean result2 = Time.isTimeMatching(recipe2, time2);

        assertTrue(result1);
        assertTrue(result2);
    }
}