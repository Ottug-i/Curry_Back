package com.ottugi.curry.domain.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ottugi.curry.TestObjectFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DifficultyTest {
    @Test
    @DisplayName("올바른 레시피 난이도 열거형 값 테스트")
    void testOfValidDifficulty() {
        String validDifficulty = Difficulty.ANYONE.getDifficulty();

        Difficulty difficulty = Difficulty.ofDifficulty(validDifficulty);

        assertNotNull(difficulty);
        assertEquals(Difficulty.ANYONE, difficulty);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 난이도 열거형 값 테스트")
    void testOfInvalidDifficulty() {
        String invalidDifficulty = "초보자";

        Difficulty difficulty = Difficulty.ofDifficulty(invalidDifficulty);

        assertNull(difficulty);
    }

    @Test
    @DisplayName("동일한 난이도 일치 테스트")
    void testIsCompositionMatching() {
        Recipe recipe = TestObjectFactory.initRecipe();
        String difficulty = Difficulty.BEGINNER.getDifficulty();

        Boolean result = Difficulty.isDifficultyMatching(recipe, difficulty);

        assertTrue(result);
    }
}