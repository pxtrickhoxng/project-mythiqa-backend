package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.config.S3Config;
import com.mythiqa.mythiqabackend.dto.request.CreateBookRequestDTO;
import com.mythiqa.mythiqabackend.dto.response.NewChapterNumDTO;
import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import com.mythiqa.mythiqabackend.repository.ChapterRepository;
import com.mythiqa.mythiqabackend.repository.UserRepository;
import com.mythiqa.mythiqabackend.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final S3Config s3Config;

    public BookService(BookRepository bookRepository, UserRepository userRepository, FileService fileService, S3Config s3Config) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.s3Config = s3Config;
    }

    public List<Book> getBooksByUserId (String userId) {
        return bookRepository.getBooksByUserId(userId);
    }

    public Book getBookByBookId (int bookId) {
        return bookRepository.getBookByBookId(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void createBook (CreateBookRequestDTO dto, Jwt jwt) {
        String requesterUserId = JwtUtils.getUserIdFromJwt(jwt);
        if (!userRepository.existsById(requesterUserId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        boolean bookExists = bookRepository.existsByBookName(dto.getBookName());
        if (bookExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book already exists");
        }

        String bookCoverUrl = null;

        if (dto.getBookCover() != null && !dto.getBookCover().isEmpty()) {
            String bookCoverObjectKey = requesterUserId + "/books/" + dto.getBookCover().getOriginalFilename();
            fileService.uploadFile(dto.getBookCover(), bookCoverObjectKey);
            bookCoverUrl = "https://" + fileService.getBucketName() + ".s3." + s3Config.getRegion() + ".amazonaws.com/" + bookCoverObjectKey;
        }

        Book book = new Book(dto, requesterUserId, bookCoverUrl);
        bookRepository.save(book);
    }
}
