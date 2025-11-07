package com.mythiqa.mythiqabackend.dto.request;

import com.mythiqa.mythiqabackend.model.RichEditor;

public class UpdateChapterDTO {
    RichEditor richEditor;

    public UpdateChapterDTO (RichEditor richEditor) {
        this.richEditor = richEditor;
    }
    public UpdateChapterDTO(){}

    public RichEditor getChapterContent() {
        return richEditor;
    }
}
