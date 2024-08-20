package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Additional methods for CRUD operations
    public List<Book> findByAuthor(String author) {
        return (List<Book>) bookRepository.findByAuthor(author);
    }
    public List<Book> findByTitleContaining(String title) {
        return (List<Book>) bookRepository.findByTitleContaining(title);
    }
}

