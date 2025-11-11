package com.mythiqa.mythiqabackend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mythiqa.mythiqabackend.model.Appearance;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class AppearanceJsonConverter implements AttributeConverter<Appearance, String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Appearance attribute) {
        if (attribute == null) return null;
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize Appearance to JSON", e);
        }
    }

    @Override
    public Appearance convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        try {
            return mapper.readValue(dbData, Appearance.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to deserialize JSON to Appearance", e);
        }
    }
}

