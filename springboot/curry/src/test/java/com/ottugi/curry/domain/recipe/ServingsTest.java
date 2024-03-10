package com.ottugi.curry.domain.recipe;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ottugi.curry.except.InvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServingsTest {
    @Test
    @DisplayName("올바른 레시피 인분 열거형 값 테스트")
    void testFindByValidServingName() {
        String validServings = Servings.ONE.getServingName();

        Servings servings = Servings.findByServingName(validServings);

        assertNotNull(servings);
        assertEquals(Servings.ONE, servings);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 인분 열거형 값 테스트")
    void testFindByInvalidServingName() {
        String invalidServings = "0.5인분";

        assertThatThrownBy(() -> Servings.findByServingName(invalidServings))
                .isInstanceOf(InvalidException.class);
    }
}