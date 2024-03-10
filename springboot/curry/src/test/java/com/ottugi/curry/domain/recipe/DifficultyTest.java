package com.ottugi.curry.domain.recipe;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ottugi.curry.except.InvalidException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DifficultyTest {
    @Test
    @DisplayName("올바른 레시피 난이도 열거형 값 테스트")
    void testFindByValidDifficultyName() {
        String validDifficulty = Difficulty.ANYONE.getDifficultyName();

        Difficulty difficulty = Difficulty.findByDifficultyName(validDifficulty);

        assertNotNull(difficulty);
        assertEquals(Difficulty.ANYONE, difficulty);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 난이도 열거형 값 테스트")
    void testFindByInvalidDifficultyName() {
        String invalidDifficulty = "초보자";

        assertThatThrownBy(() -> Difficulty.findByDifficultyName(invalidDifficulty))
                .isInstanceOf(InvalidException.class);
    }

    @ParameterizedTest
    @MethodSource("provideDifficultyTestValue")
    @DisplayName("동일한 난이도 일치 테스트")
    void testMatchesDifficulty(String difficulty) {
        Recipe recipe = RecipeTest.initRecipe();

        boolean result = Difficulty.matchesDifficulty(recipe, difficulty);

        assertTrue(result);
    }

    static Stream<Arguments> provideDifficultyTestValue() {
        return Stream.of(
                Arguments.of(Difficulty.BEGINNER.getDifficultyName()),
                Arguments.of(""),
                null
        );
    }
}