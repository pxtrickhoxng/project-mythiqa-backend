package com.mythiqa.mythiqabackend.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mythiqa.mythiqabackend.model.RichEditor;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RichEditorConverter implements AttributeConverter<RichEditor, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(RichEditor richEditor) {
        if (richEditor == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(richEditor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting ChapterContent to JSON", e);
        }
    }

    @Override
    public RichEditor convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, RichEditor.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting String to ChapterContent", e);
        }
    }
}
