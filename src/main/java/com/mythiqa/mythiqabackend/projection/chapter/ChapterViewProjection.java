package com.mythiqa.mythiqabackend.projection.chapter;

import java.time.LocalDate;

public interface ChapterViewProjection {
    // Current chapter data
    String getCurrentChapterId();
    String getCurrentChapterName();
    Integer getCurrentChapterNumber();
    String getCurrentChapterContent();
    LocalDate getCurrentCreatedAt();

    // Previous chapter metadata
    String getPrevChapterId();
    String getPrevChapterName();
    Integer getPrevChapterNumber();

    // Next chapter metadata
    String getNextChapterId();
    String getNextChapterName();
    Integer getNextChapterNumber();
}
