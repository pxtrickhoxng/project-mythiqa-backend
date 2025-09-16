package com.mythiqa.mythiqabackend.model;

import com.mythiqa.mythiqabackend.dto.request.CreateNoteDTO;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @Column(name = "note_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String noteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "note_content", nullable = false)
    private String noteContent;

    @ElementCollection
    @CollectionTable(name = "note_tags", joinColumns = @JoinColumn(name = "note_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column(name = "favorited", nullable = false)
    private boolean favorited;

    @Column(name = "checked", nullable = false)
    private boolean checked;

    // Convert to list of strings after retrieval from DB
    @Column(name = "image_url")
    private String imageUrls;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    public Note () {}

    public Note (CreateNoteDTO dto, Book book, String imgUrls) {
        this.book = book;
        this.userId = dto.getUserId();
        this.title = dto.getTitle();
        this.noteContent = dto.getNoteContent();
        this.tags = dto.getTags();
        this.favorited = dto.isFavorited();
        this.checked = dto.isChecked();
        this.imageUrls = imgUrls;
        this.createdAt = LocalDate.now();
    }

    public String getNoteId() { return noteId; }
    public void setNoteId(String noteId) { this.noteId = noteId; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

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

    public String getImageUrls() { return imageUrls; }
    public void setImageUrls(String imageUrls) { this.imageUrls = imageUrls; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
}
