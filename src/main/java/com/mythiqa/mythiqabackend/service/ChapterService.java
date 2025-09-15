package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.dto.request.CreateChapterDTO;
import com.mythiqa.mythiqabackend.dto.response.chapter.GetChapterDTO;
import com.mythiqa.mythiqabackend.dto.response.chapter.NewChapterNumDTO;
import com.mythiqa.mythiqabackend.dto.response.chapter.ReadChapterDTO;
import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.model.Chapter;
import com.mythiqa.mythiqabackend.projection.chapter.ChapterViewProjection;
import com.mythiqa.mythiqabackend.projection.chapter.ChapterMetadataProjection;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import com.mythiqa.mythiqabackend.repository.ChapterRepository;
import com.mythiqa.mythiqabackend.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<GetChapterDTO> getAllChaptersByBookId(int bookId) {
        List<ChapterMetadataProjection> projections = chapterRepository.getAllByBook_BookId(bookId);
        return projections.stream()
                        .map(projection -> new GetChapterDTO.Builder()
                                .chapterId(projection.getChapterId())
                                .chapterNumber(String.valueOf(projection.getChapterNumber()))
                                .chapterName(projection.getChapterName())
                                .createdAt(projection.getCreatedAt())
                                .build())
                .collect(Collectors.toList());


    }

    public ReadChapterDTO getChapterViewByChapterId(int bookId, String chapterId) {

        ChapterViewProjection chapterViewProjection = chapterRepository.findChapterView(bookId, chapterId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chapter not found"));

        GetChapterDTO currentChapter = GetChapterDTO.builder()
                .chapterId(chapterViewProjection.getCurrentChapterId())
                .chapterName(chapterViewProjection.getCurrentChapterName())
                .chapterNumber(String.valueOf(chapterViewProjection.getCurrentChapterNumber()))
                .chapterContent(chapterViewProjection.getCurrentChapterContent())
                .createdAt(chapterViewProjection.getCurrentCreatedAt())
                .build();

        ReadChapterDTO.ChapterSummaryDTO prevChapter = null;
        if (chapterViewProjection.getPrevChapterId() != null) {
            prevChapter = ReadChapterDTO.ChapterSummaryDTO.builder()
                    .chapterId(chapterViewProjection.getPrevChapterId())
                    .chapterName(chapterViewProjection.getPrevChapterName())
                    .chapterNumber(chapterViewProjection.getPrevChapterNumber())
                    .build();
        }

        ReadChapterDTO.ChapterSummaryDTO nextChapter = null;
        if (chapterViewProjection.getNextChapterId() != null) {
            nextChapter = ReadChapterDTO.ChapterSummaryDTO.builder()
                    .chapterId(chapterViewProjection.getNextChapterId())
                    .chapterName(chapterViewProjection.getNextChapterName())
                    .chapterNumber(chapterViewProjection.getNextChapterNumber())
                    .build();
        }

        return ReadChapterDTO.builder()
                .currentChapter(currentChapter)
                .prevChapter(prevChapter)
                .nextChapter(nextChapter)
                .build();
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
