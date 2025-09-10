package com.mythiqa.mythiqabackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class CreateBookRequestDTO {
    @NotBlank
    private String bookName;

    private String bookType;

    private String description;

    private String genre;

    private String targetAudience;

    private String contentWarnings;

    private MultipartFile bookCover;

    public CreateBookRequestDTO() {}

    public CreateBookRequestDTO(String bookName, String bookType, String description, String genre, String targetAudience, String contentWarnings, MultipartFile bookCover) {
        this.bookName = bookName;
        this.bookType = bookType;
        this.description = description;
        this.genre = genre;
        this.targetAudience = targetAudience;
        this.contentWarnings = contentWarnings;
        this.bookCover = bookCover;
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

    public MultipartFile getBookCover() {
        return bookCover;
    }

    public void setBookCover(MultipartFile bookCover) {
        this.bookCover = bookCover;
    }
}
