package com.mythiqa.mythiqabackend.dto.response.note;

import java.time.LocalDate;
import java.util.List;

public class NoteDTO {
    private String noteId;
    private int bookId;
    private String userId;
    private String title;
    private String noteContent;
    private List<String> tags;
    private boolean favorited;
    private boolean checked;
    private String imageUrls;
    private LocalDate createdAt;

    private NoteDTO(Builder builder) {
        this.noteId = builder.noteId;
        this.bookId = builder.bookId;
        this.userId = builder.userId;
        this.title = builder.title;
        this.noteContent = builder.noteContent;
        this.tags = builder.tags;
        this.favorited = builder.favorited;
        this.checked = builder.checked;
        this.imageUrls = builder.imageUrls;
        this.createdAt = builder.createdAt;
    }

    public String getNoteId() { return noteId; }
    public int getBookId() { return bookId; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getNoteContent() { return noteContent; }
    public List<String> getTags() { return tags; }
    public boolean isFavorited() { return favorited; }
    public boolean isChecked() { return checked; }
    public String getImageUrls() { return imageUrls; }
    public LocalDate getCreatedAt() {return createdAt;}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String noteId;
        private int bookId;
        private String userId;
        private String title;
        private String noteContent;
        private List<String> tags;
        private boolean favorited;
        private boolean checked;
        private String imageUrls;
        private LocalDate createdAt;

        public Builder noteId(String noteId) {
            this.noteId = noteId;
            return this;
        }
        public Builder bookId(int bookId) {
            this.bookId = bookId;
            return this;
        }
        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder noteContent(String noteContent) {
            this.noteContent = noteContent;
            return this;
        }
        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }
        public Builder favorited(boolean favorited) {
            this.favorited = favorited;
            return this;
        }
        public Builder checked(boolean checked) {
            this.checked = checked;
            return this;
        }
        public Builder imageUrls(String imageUrls) {
            this.imageUrls = imageUrls;
            return this;
        }

        public Builder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NoteDTO build() {
            return new NoteDTO(this);
        }
    }
}
