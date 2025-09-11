package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.dto.request.CreateChapterDTO;
import com.mythiqa.mythiqabackend.dto.response.NewChapterNumDTO;
import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.model.Chapter;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import com.mythiqa.mythiqabackend.repository.ChapterRepository;
import com.mythiqa.mythiqabackend.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;

    public ChapterService (ChapterRepository chapterRepository, BookRepository bookRepository) {
        this.chapterRepository = chapterRepository;
        this.bookRepository = bookRepository;
    }

    public NewChapterNumDTO getNewChapterNumByBookId(int bookId) {
        long chapterCount = chapterRepository.countByBook_BookId(bookId);
        return new NewChapterNumDTO(chapterCount + 1);
    }

    public void createNewChapter(CreateChapterDTO chapterDTO, Jwt jwt) {
        String requesterUserId = JwtUtils.getUserIdFromJwt(jwt);

        Book book = bookRepository.getBookByBookId(chapterDTO.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!book.getUserId().equals(requesterUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Chapter chapter = new Chapter(chapterDTO, book);
        chapterRepository.save(chapter);
    }
}
