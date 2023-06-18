package com.ottugi.curry.domain.recipe;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TimeConverter implements AttributeConverter<Time, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Time attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("Time이 null입니다.");
        }

        return attribute.getTime();
    }

    @Override
    public Time convertToEntityAttribute(Integer dbData) {

        if (dbData == null) {
            throw new IllegalArgumentException("dbData가 비어있습니다.");
        }

        return Time.ofTimeName(dbData);
    }

}