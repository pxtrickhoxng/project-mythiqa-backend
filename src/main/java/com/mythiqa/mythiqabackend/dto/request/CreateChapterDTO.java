package com.mythiqa.mythiqabackend.dto.request;

import com.mythiqa.mythiqabackend.model.RichEditor;

public class CreateChapterDTO {
    String chapterName;
    int chapterNumber;
    int bookId;
    RichEditor richEditor;

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

    public RichEditor getChapterContent() {
        return richEditor;
    }

    public void setChapterContent(RichEditor richEditor) {
        this.richEditor = richEditor;
    }
}
