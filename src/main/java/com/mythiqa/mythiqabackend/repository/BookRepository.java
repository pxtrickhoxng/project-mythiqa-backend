package com.mythiqa.mythiqabackend.repository;


import com.mythiqa.mythiqabackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    long countByUserId(String userId);
    List<Book> getBooksByUserId(String userId);
}
