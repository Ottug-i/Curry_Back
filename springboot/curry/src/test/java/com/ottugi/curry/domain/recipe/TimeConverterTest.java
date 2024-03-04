package com.ottugi.curry.domain.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeConverterTest {
    private final TimeConverter timeConverter = new TimeConverter();

    @Test
    @DisplayName("시간 열거형으로 정수형 데이터베이스 값으로 변환 테스트")
    void testConvertToDatabaseColumn() {
        Time time = Time.FIVE_MINUTES;

        int dbData = timeConverter.convertToDatabaseColumn(time);

        assertEquals(Time.FIVE_MINUTES.getTime(), dbData);
    }

    @Test
    @DisplayName("정수형 데이터베이스 값을 시간 열거형으로 변환 테스트")
    void testConvertToEntityAttribute() {
        int dbData = Time.FIVE_MINUTES.getTime();

        Time time = timeConverter.convertToEntityAttribute(dbData);

        assertNotNull(time);
        assertEquals(Time.FIVE_MINUTES, time);
    }
}