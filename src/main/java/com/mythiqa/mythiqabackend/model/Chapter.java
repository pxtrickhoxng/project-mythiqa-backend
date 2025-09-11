package com.mythiqa.mythiqabackend.model;

import com.mythiqa.mythiqabackend.converter.ChapterContentConverter;
import com.mythiqa.mythiqabackend.dto.request.CreateChapterDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "chapters")
public class Chapter {

    @Id
    @Column(name = "chapter_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String chapterId;

    @Column(name = "chapter_number", nullable = false)
    private int chapterNumber;

    @Column(name = "chapter_name", nullable = false)
    private String chapterName;

    @Column(name = "chapter_content", columnDefinition = "text")
    @Convert(converter = ChapterContentConverter.class)
    private ChapterContent chapterContent;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Chapter () {}

    public Chapter (CreateChapterDTO dto, Book book) {
        this.chapterNumber = dto.getChapterNumber();
        this.chapterName = dto.getChapterName();
        this.chapterContent = dto.getChapterContent();
        this.book = book;
        this.createdAt = LocalDate.now();
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public ChapterContent getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(ChapterContent chapterContent) {
        this.chapterContent = chapterContent;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

