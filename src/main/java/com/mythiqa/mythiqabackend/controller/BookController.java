package com.mythiqa.mythiqabackend.controller;


import com.mythiqa.mythiqabackend.dto.request.CreateBookRequestDTO;
import com.mythiqa.mythiqabackend.dto.response.NewChapterNumDTO;
import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/user/{userId}")
    public List<Book> getUserBooks(@PathVariable String userId) {
        return bookService.getBooksByUserId(userId);
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable int bookId) {
        return bookService.getBookByBookId(bookId);
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@ModelAttribute CreateBookRequestDTO createBookRequest, @AuthenticationPrincipal Jwt jwt) {
        bookService.createBook(createBookRequest, jwt);
        URI location = URI.create("/api/books");
        return ResponseEntity.created(location).build();
    }

    //TODO
    //create fetchBookChapters & createChapter endpoint
    // reorganize frontend so that /api is under /src/api
    // and types is under /src/types
}
