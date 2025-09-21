package com.mythiqa.mythiqabackend.repository;

import com.mythiqa.mythiqabackend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, String> {

    Optional<Note> findByUserIdAndBookId(String userId, Integer bookId);
}
