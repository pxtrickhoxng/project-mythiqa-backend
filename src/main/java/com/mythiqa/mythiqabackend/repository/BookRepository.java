package com.mythiqa.mythiqabackend.repository;


import com.mythiqa.mythiqabackend.model.Book;
import com.mythiqa.mythiqabackend.projection.book.PublicBookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    long countByUserId(String userId);
    List<Book> getBooksByUserId(String userId);
    Optional<PublicBookProjection> getPublicBookByBookId(Integer bookId);
    Optional<Book> getBookByBookId(Integer bookId);
    boolean existsByBookName(String bookName);
    boolean existsByBookId(Integer bookId);
}
