package com.mythiqa.mythiqabackend.dto.request;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateNoteDTO {
    private int bookId;
    @NotNull
    private String userId;
    private String title;
    @NotNull
    private String noteContent;
    private List<String> tags;
    @NotNull
    private boolean favorited;
    @NotNull
    private boolean checked;
    private List<MultipartFile> images;

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getNoteContent() { return noteContent; }
    public void setNoteContent(String noteContent) { this.noteContent = noteContent; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public boolean isFavorited() { return favorited; }
    public void setFavorited(boolean favorited) { this.favorited = favorited; }

    public boolean isChecked() { return checked; }
    public void setChecked(boolean checked) { this.checked = checked; }

    public List<MultipartFile> getImages() { return images; }
    public void setImages(List<MultipartFile> images) { this.images = images; }
}
