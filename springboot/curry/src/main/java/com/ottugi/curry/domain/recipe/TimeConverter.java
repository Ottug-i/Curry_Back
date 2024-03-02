package com.ottugi.curry.domain.recipe;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TimeConverter implements AttributeConverter<Time, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Time time) {
        return time.getTime();
    }

    @Override
    public Time convertToEntityAttribute(Integer dbData) {
        return Time.ofTimeName(dbData);
    }
}