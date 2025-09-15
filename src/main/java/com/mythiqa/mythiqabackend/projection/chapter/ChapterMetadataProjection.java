package com.mythiqa.mythiqabackend.projection.chapter;

import java.time.LocalDate;

public interface ChapterMetadataProjection {
    String getChapterId();
    String getChapterName();
    Integer getChapterNumber();
    LocalDate getCreatedAt();
}
