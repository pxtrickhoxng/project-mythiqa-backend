package com.mythiqa.mythiqabackend.projection.book;

import java.time.LocalDate;

public interface PublicBookProjection {
    int getBookId();
    String getUserId();
    String getBookName();
    String getBookType();
    String getDescription();
    String getGenre();
    String getTargetAudience();
    String getContentWarnings();
    String getBookCoverUrl();
    LocalDate getCreatedAt();
}
