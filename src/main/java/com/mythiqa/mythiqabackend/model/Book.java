package com.mythiqa.mythiqabackend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "book_name", length = 255, nullable = false)
    private String bookName;

    @Column(name = "book_type", length = 50)
    private String bookType;

    @Column(name = "description")
    private String description;

    @Column(name = "genre", length = 100)
    private String genre;

    @Column(name = "target_audience", length = 50)
    private String targetAudience;

    // Store as string, convert it to an array before sending to frontend
    @Column(name = "content_warnings")
    private String contentWarnings;

    @Column(name = "book_cover_url", length = 255)
    private String bookCoverUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getContentWarnings() {
        return contentWarnings;
    }

    public void setContentWarnings(String contentWarnings) {
        this.contentWarnings = contentWarnings;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public void setBookCoverUrl(String bookCoverUrl) {
        this.bookCoverUrl = bookCoverUrl;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
