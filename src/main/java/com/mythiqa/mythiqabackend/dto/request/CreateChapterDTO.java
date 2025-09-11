package com.mythiqa.mythiqabackend.dto.request;

import com.mythiqa.mythiqabackend.model.ChapterContent;

public class CreateChapterDTO {
    String chapterName;
    int chapterNumber;
    int bookId;
    ChapterContent chapterContent;

    public CreateChapterDTO() {}

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public ChapterContent getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(ChapterContent chapterContent) {
        this.chapterContent = chapterContent;
    }
}
