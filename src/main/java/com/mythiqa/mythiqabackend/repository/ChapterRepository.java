package com.mythiqa.mythiqabackend.repository;

import com.mythiqa.mythiqabackend.model.Chapter;
import com.mythiqa.mythiqabackend.projection.chapter.ChapterMetadataProjection;
import com.mythiqa.mythiqabackend.projection.chapter.ChapterViewProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, String > {
    long countByBook_BookId(Integer bookId);
    List<ChapterMetadataProjection> getAllByBook_BookId(Integer bookId);

    @Query(value = """
        SELECT 
          c.chapter_id AS currentChapterId,
          c.chapter_name AS currentChapterName,
          c.chapter_number AS currentChapterNumber,
          c.chapter_content AS currentChapterContent,
          c.created_at AS currentCreatedAt,
          
          prev.chapter_id AS prevChapterId,
          prev.chapter_name AS prevChapterName,
          prev.chapter_number AS prevChapterNumber,
          
          next.chapter_id AS nextChapterId,
          next.chapter_name AS nextChapterName,
          next.chapter_number AS nextChapterNumber
          
        FROM chapters c
        LEFT JOIN chapters prev ON prev.book_id = c.book_id AND prev.chapter_number = (
            SELECT MAX(chapter_number) FROM chapters WHERE book_id = c.book_id AND chapter_number < c.chapter_number
        )
        LEFT JOIN chapters next ON next.book_id = c.book_id AND next.chapter_number = (
            SELECT MIN(chapter_number) FROM chapters WHERE book_id = c.book_id AND chapter_number > c.chapter_number
        )
        WHERE c.book_id = :bookId AND c.chapter_id = :chapterId
        """, nativeQuery = true)
    Optional<ChapterViewProjection> findChapterView(@Param("bookId") Integer bookId, @Param("chapterId") String chapterId);
}
