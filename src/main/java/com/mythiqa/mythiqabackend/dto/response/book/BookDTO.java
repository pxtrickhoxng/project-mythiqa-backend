package com.mythiqa.mythiqabackend.dto.response.book;

import java.time.LocalDate;

public class BookDTO {
    int bookId;
    String userId;
    String bookName;
    String bookType;
    String description;
    String genre;
    String targetAudience;
    String contentWarnings;
    String bookCoverUrl;
    LocalDate createdAt;

    public int getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookType() {
        return bookType;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public String getContentWarnings() {
        return contentWarnings;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    private BookDTO(Builder builder) {
        this.bookId = builder.bookId;
        this.userId = builder.userId;
        this.bookName = builder.bookName;
        this.bookType = builder.bookType;
        this.description = builder.description;
        this.genre = builder.genre;
        this.targetAudience = builder.targetAudience;
        this.contentWarnings = builder.contentWarnings;
        this.bookCoverUrl = builder.bookCoverUrl;
        this.createdAt = builder.createdAt;
    }

    public static class Builder {
        private int bookId;
        private String userId;
        private String bookName;
        private String bookType;
        private String description;
        private String genre;
        private String targetAudience;
        private String contentWarnings;
        private String bookCoverUrl;
        private LocalDate createdAt;

        public Builder bookId(int bookId) {
            this.bookId = bookId;
            return this;
        }
        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }
        public Builder bookType(String bookType) {
            this.bookType = bookType;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }
        public Builder targetAudience(String targetAudience) {
            this.targetAudience = targetAudience;
            return this;
        }
        public Builder contentWarnings(String contentWarnings) {
            this.contentWarnings = contentWarnings;
            return this;
        }
        public Builder bookCoverUrl(String bookCoverUrl) {
            this.bookCoverUrl = bookCoverUrl;
            return this;
        }
        public Builder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public BookDTO build() {
            return new BookDTO(this);
        }
    }
}
