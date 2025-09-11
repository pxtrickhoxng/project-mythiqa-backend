package com.mythiqa.mythiqabackend.repository;

import com.mythiqa.mythiqabackend.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, String > {
    long countByBook_BookId(Integer bookId);
}
