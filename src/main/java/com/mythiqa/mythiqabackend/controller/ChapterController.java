package com.mythiqa.mythiqabackend.controller;

import com.mythiqa.mythiqabackend.dto.request.CreateChapterDTO;
import com.mythiqa.mythiqabackend.dto.response.chapter.GetChapterDTO;
import com.mythiqa.mythiqabackend.dto.response.chapter.ReadChapterDTO;
import com.mythiqa.mythiqabackend.dto.response.chapter.NewChapterNumDTO;
import com.mythiqa.mythiqabackend.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/chapters")
public class ChapterController {
    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("/{bookId}/new-chapter-num")
    public NewChapterNumDTO getChapterNum(@PathVariable int bookId) {
        return chapterService.getNewChapterNumByBookId(bookId);
    }

    @GetMapping("/{bookId}")
    public List<GetChapterDTO> getAllChapters(@PathVariable int bookId) {
        return chapterService.getAllChaptersByBookId(bookId);
    }

    @PostMapping
    public ResponseEntity<Void> createNewChapter(@RequestBody CreateChapterDTO chapterDTO, @AuthenticationPrincipal Jwt jwt) {
        chapterService.createNewChapter(chapterDTO, jwt);
        URI location = URI.create("/api/chapters");
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/read/{bookId}/{chapterId}")
    public ReadChapterDTO getOneChapter(@PathVariable int bookId, @PathVariable String chapterId) {
        return chapterService.getChapterViewByChapterId(bookId, chapterId);
    }
    // Return these:
    // {chapter, prevChapter, nextChapter}
    // where chapter == all content, while the others are only chapterName + chapterNumber + chapterId
}
