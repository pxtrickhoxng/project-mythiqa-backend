package com.mythiqa.mythiqabackend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mythiqa.mythiqabackend.model.Book.ChapterContent;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ChapterContentConverter implements AttributeConverter<ChapterContent, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ChapterContent attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting ChapterContent to String", e);
        }
    }

    @Override
    public ChapterContent convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ChapterContent.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting String to ChapterContent", e);
        }
    }
}
