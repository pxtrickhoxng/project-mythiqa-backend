package com.mythiqa.mythiqabackend.dto.request;

import com.mythiqa.mythiqabackend.model.ChapterContent;

public class UpdateChapterDTO {
    ChapterContent chapterContent;

    public UpdateChapterDTO (ChapterContent chapterContent) {
        this.chapterContent = chapterContent;
    }
    public UpdateChapterDTO(){}

    public ChapterContent getChapterContent() {
        return chapterContent;
    }
}
