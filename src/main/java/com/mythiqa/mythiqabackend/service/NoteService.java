package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.config.S3Config;
import com.mythiqa.mythiqabackend.dto.request.CreateNoteDTO;
import com.mythiqa.mythiqabackend.dto.response.note.NoteDTO;
import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.model.Note;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import com.mythiqa.mythiqabackend.repository.NoteRepository;
import com.mythiqa.mythiqabackend.util.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final BookRepository bookRepository;
    private final FileService fileService;
    private final S3Config s3Config;

    public NoteService(NoteRepository noteRepository, BookRepository bookRepository, FileService fileService, S3Config s3Config) {
        this.noteRepository = noteRepository;
        this.bookRepository = bookRepository;
        this.fileService = fileService;
        this.s3Config = s3Config;
    }

    public NoteDTO getNoteByBookId(int bookId, Jwt jwt) {
        String requesterUserId = JwtUtils.getUserIdFromJwt(jwt);
        Note note = noteRepository.findByUserIdAndBookId(requesterUserId, bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find note that matches the sent userId and bookId"));
        return new NoteDTO.Builder()
                .noteId(note.getNoteId())
                .bookId(note.getBookId())
                .title(note.getTitle())
                .noteContent(note.getNoteContent())
                .tags(note.getTags())
                .favorited(note.isFavorited())
                .checked(note.isChecked())
                .imageUrls(note.getImageUrls())
                .createdAt(note.getCreatedAt())
                .build();
    }

    public void createNoteByBookId(CreateNoteDTO dto, Jwt jwt) {
        String requesterUserId = JwtUtils.getUserIdFromJwt(jwt);

        String imgUrls = null;
        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            String baseUrl = "https://" + fileService.getBucketName() + ".s3." + s3Config.getRegion() + ".amazonaws.com/";
            List<String> urls = new ArrayList<>();

            for (MultipartFile file : dto.getImages()) {
                String objectKey = requesterUserId + "/notes/" + dto.getBookId() + "/" + file.getOriginalFilename();
                fileService.uploadFile(file, objectKey);
                urls.add(baseUrl + objectKey);
            }
            imgUrls = String.join(",", urls);
        }

        Note note = new Note(dto, imgUrls);
        noteRepository.save(note);
    }
}
