package com.geekbrains.minimarket.service;

import com.geekbrains.minimarket.dto.BookDto;
import com.geekbrains.minimarket.entites.Book;
import com.geekbrains.minimarket.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getOneById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(BookDto bookDto) {
        Book b = new Book();
        b.setId(bookDto.getId());
        b.setTitle(bookDto.getTitle());
        b.setAuthor(authorService.findByName(bookDto.getAuthor()).get());
        return bookRepository.save(b);
    }

    public boolean existById(Long id) {
        return bookRepository.existsById(id);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
