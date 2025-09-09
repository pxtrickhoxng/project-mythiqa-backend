package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksByUserId (String userId) {
        return bookRepository.getBooksByUserId(userId);
    }
}
