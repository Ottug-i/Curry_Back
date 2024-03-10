package com.ottugi.curry.domain.recipe;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TimeConverter implements AttributeConverter<Time, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Time entityTime) {
        return entityTime.getTimeInMinutes();
    }

    @Override
    public Time convertToEntityAttribute(Integer databaseTimeInMinutes) {
        return Time.findByTimeInMinutes(databaseTimeInMinutes);
    }
}