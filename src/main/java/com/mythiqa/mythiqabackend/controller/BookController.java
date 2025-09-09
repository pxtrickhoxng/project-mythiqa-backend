package com.mythiqa.mythiqabackend.controller;


import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{userId}/stories")
    public List<Book> getUserStories(@PathVariable String userId) {
        return bookService.getBooksByUserId(userId);
    }

    @PostMapping("/{userId}/create-story")
    public void createStory() {

    }
}
