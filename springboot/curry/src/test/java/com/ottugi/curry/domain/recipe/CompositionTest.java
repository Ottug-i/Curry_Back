package com.ottugi.curry.domain.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompositionTest {
    @Test
    @DisplayName("올바른 레시피 구성 열거형 값 테스트")
    void testOfValidComposition() {
        String validComposition = Composition.LIGHTLY.getComposition();

        Composition composition = Composition.ofComposition(validComposition);

        assertNotNull(composition);
        assertEquals(Composition.LIGHTLY, composition);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 구성 열거형 값 테스트")
    void testOfInvalidComposition() {
        String invalidComposition = "부족하게";

        Composition composition = Composition.ofComposition(invalidComposition);

        assertNull(composition);
    }

    @Test
    @DisplayName("동일한 구성 일치 테스트")
    void testIsCompositionMatching() {
        Recipe recipe = RecipeTest.initRecipe();
        String composition = Composition.LIGHTLY.getComposition();

        Boolean result = Composition.isCompositionMatching(recipe, composition);

        assertTrue(result);
    }
}