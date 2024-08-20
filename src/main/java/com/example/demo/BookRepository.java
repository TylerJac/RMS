package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Custom query methods for finding books by author or title
    Iterable<Book> findByAuthor(String author);
    Iterable<Book> findByTitleContaining(String title);
}
