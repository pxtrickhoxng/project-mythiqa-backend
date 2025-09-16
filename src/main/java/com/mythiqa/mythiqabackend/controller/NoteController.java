package com.mythiqa.mythiqabackend.controller;

import com.mythiqa.mythiqabackend.dto.request.CreateNoteDTO;
import com.mythiqa.mythiqabackend.dto.response.note.NoteDTO;
import com.mythiqa.mythiqabackend.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{bookId}")
    public NoteDTO getBookNotes (@PathVariable int bookId, @AuthenticationPrincipal Jwt jwt) {
        return noteService.getNoteByBookId(bookId, jwt);
    }

    @PostMapping
    public ResponseEntity<Void> createNote (@ModelAttribute CreateNoteDTO createNoteDTO, @PathVariable int bookId, @AuthenticationPrincipal Jwt jwt) {
        noteService.createNoteByBookId(createNoteDTO, jwt);
        URI location = URI.create("/api/notes");
        return ResponseEntity.created(location).build();
    }
}
