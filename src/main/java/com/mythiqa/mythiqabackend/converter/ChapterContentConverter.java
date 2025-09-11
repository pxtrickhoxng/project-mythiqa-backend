package com.mythiqa.mythiqabackend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mythiqa.mythiqabackend.model.ChapterContent;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ChapterContentConverter implements AttributeConverter<ChapterContent, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ChapterContent chapterContent) {
        if (chapterContent == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(chapterContent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting ChapterContent to JSON", e);
        }
    }

    @Override
    public ChapterContent convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, ChapterContent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting String to ChapterContent", e);
        }
    }
}
