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

class TimeTest {
    @Test
    @DisplayName("올바른 레시피 시간 이름 열거형 값 테스트")
    void testFindByValidTimeName() {
        String validTime = Time.FIFTEEN_MINUTES.getTimeName();

        Time time = Time.findByTimeName(validTime);

        assertNotNull(time);
        assertEquals(Time.FIFTEEN_MINUTES, time);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 시간 이름 열거형 값 테스트")
    void testFindByInvalidTimeName() {
        String invalidTime = "25분 이내";

        assertThatThrownBy(() -> Time.findByTimeName(invalidTime))
                .isInstanceOf(InvalidException.class);
    }

    @Test
    @DisplayName("올바른 레시피 시간 분 열거형 값 테스트")
    void testFindByValidTimeInMinutes() {
        int validTime = 15;

        Time time = Time.findByTimeInMinutes(validTime);

        assertNotNull(time);
        assertEquals(Time.FIFTEEN_MINUTES, time);
    }

    @Test
    @DisplayName("올바르지 않은 레시피 시간 분 열거형 값 테스트")
    void testFindByInvalidTimeInMinutes() {
        int invalidTime = 25;

        assertThatThrownBy(() -> Time.findByTimeInMinutes(invalidTime))
                .isInstanceOf(InvalidException.class);
    }

    @ParameterizedTest
    @MethodSource("provideTimeTestValue")
    @DisplayName("동일한 시간 일치 테스트")
    void testIsTimeMatching(String time) {
        Recipe recipe = RecipeTest.initRecipe();

        Boolean result = Time.matchesTime(recipe, time);

        assertTrue(result);
    }

    static Stream<Arguments> provideTimeTestValue() {
        return Stream.of(
                Arguments.of(Time.SIXTY_MINUTES.getTimeName()),
                Arguments.of(""),
                null
        );
    }
}