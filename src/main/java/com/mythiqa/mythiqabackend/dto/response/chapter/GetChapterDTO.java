package com.mythiqa.mythiqabackend.dto.response.chapter;

import java.time.LocalDate;

public class GetChapterDTO {
    private String chapterId;
    private String chapterNumber;
    private String chapterContent;
    private String chapterName;
    private LocalDate createdAt;

    private GetChapterDTO(Builder builder) {
        this.chapterId = builder.chapterId;
        this.chapterNumber = builder.chapterNumber;
        this.chapterContent = builder.chapterContent;
        this.chapterName = builder.chapterName;
        this.createdAt = builder.createdAt;
    }

    public String getChapterId() {
        return chapterId;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public String getChapterName() {
        return chapterName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String chapterId;
        private String chapterNumber;
        private String chapterContent;
        private String chapterName;
        private LocalDate createdAt;

        public Builder chapterId(String chapterId) {
            this.chapterId = chapterId;
            return this;
        }

        public Builder chapterNumber(String chapterNumber) {
            this.chapterNumber = chapterNumber;
            return this;
        }

        public Builder chapterContent(String chapterContent) {
            this.chapterContent = chapterContent;
            return this;
        }

        public Builder chapterName(String chapterName) {
            this.chapterName = chapterName;
            return this;
        }

        public Builder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public GetChapterDTO build() {
            return new GetChapterDTO(this);
        }
    }
}
