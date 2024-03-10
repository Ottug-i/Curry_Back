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

class CompositionTest {
    @Test
    @DisplayName("올바른 레시피 구성 열거형 값 테스트")
    void testFindByValidCompositionName() {
        String validComposition = Composition.LIGHTLY.getCompositionName();

        Composition composition = Composition.findByCompositionName(validComposition);

        assertNotNull(composition);
        assertEquals(Composition.LIGHTLY, composition);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 구성 열거형 값 테스트")
    void testFindByInvalidCompositionName() {
        String invalidComposition = "부족하게";

        assertThatThrownBy(() -> Composition.findByCompositionName(invalidComposition))
                .isInstanceOf(InvalidException.class);
    }

    @ParameterizedTest
    @MethodSource("provideCompositionTestValue")
    @DisplayName("동일한 구성 일치 테스트")
    void testMatchesComposition(String composition) {
        Recipe recipe = RecipeTest.initRecipe();

        boolean result = Composition.matchesComposition(recipe, composition);

        assertTrue(result);
    }

    static Stream<Arguments> provideCompositionTestValue() {
        return Stream.of(
                Arguments.of(Composition.LIGHTLY.getCompositionName()),
                Arguments.of(""),
                null
        );
    }
}