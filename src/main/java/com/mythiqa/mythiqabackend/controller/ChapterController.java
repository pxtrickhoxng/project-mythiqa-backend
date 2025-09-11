package com.mythiqa.mythiqabackend.controller;

import com.mythiqa.mythiqabackend.dto.request.CreateChapterDTO;
import com.mythiqa.mythiqabackend.dto.response.NewChapterNumDTO;
import com.mythiqa.mythiqabackend.model.ChapterContent;
import com.mythiqa.mythiqabackend.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @PostMapping
    public ResponseEntity<Void> createNewChapter(@RequestBody CreateChapterDTO chapterDTO, @AuthenticationPrincipal Jwt jwt) {
        chapterService.createNewChapter(chapterDTO, jwt);
        URI location = URI.create("/api/chapters");
        return ResponseEntity.created(location).build();
    }
}
