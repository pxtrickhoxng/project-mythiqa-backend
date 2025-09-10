package com.mythiqa.mythiqabackend.repository;


import com.mythiqa.mythiqabackend.model.Book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    long countByUserId(String userId);
    List<Book> getBooksByUserId(String userId);
    Optional<Book> getBookByBookId(int bookId);
    boolean existsByBookName(String bookName);
}
