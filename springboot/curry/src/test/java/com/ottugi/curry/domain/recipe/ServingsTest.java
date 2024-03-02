package com.ottugi.curry.domain.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServingsTest {
    @Test
    @DisplayName("올바른 레시피 인분 열거형 값 테스트")
    void testOfValidServings() {
        String validServings = Servings.ONE.getServings();

        Servings servings = Servings.ofServings(validServings);

        assertNotNull(servings);
        assertEquals(Servings.ONE, servings);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 인분 열거형 값 테스트")
    void testOfInvalidServings() {
        String invalidServings = "0.5인분";

        Servings servings = Servings.ofServings(invalidServings);

        assertNull(servings);
    }
}