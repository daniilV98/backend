package com.geekbrains.minimarket.controllers;

import com.geekbrains.minimarket.dto.BookDto;
import com.geekbrains.minimarket.entites.Book;
import com.geekbrains.minimarket.exceptions.MarketError;
import com.geekbrains.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.minimarket.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
@Api("Set of endpoints for books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Get all books
     * Example: GET http://localhost:8189/market/api/v1/books
     * */
    @GetMapping
    @ApiOperation("Return all books")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks().stream().map(BookDto::new).collect(Collectors.toList());
    }

    /**
     * Get book by id
     * Example: GET http://localhost:8189/market/api/v1/books/1
     * */
    @GetMapping("/{id}")
    @ApiOperation("Returns a specific book by their identifier. 404 if doesn't exist.")
    public BookDto getBooksById(@ApiParam("Id of the book to be obtained. Can not be empty.") @PathVariable Long id) {
        Book book = bookService.getOneById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find book with id: " + id));
        return new BookDto(book);
    }

    /**
     * Create new book
     * Example: POST http://localhost:8189/market/api/v1/books
     * */
    @PostMapping
    @ApiOperation("Creates a new book. If id != null, then throw bad request response")
    public ResponseEntity<?> createNewBooks(@RequestBody Book book) {
        if(book.getId() != null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    /**
     * Modify book
     * Example: PUT http://localhost:8189/market/api/v1/books
     * */
    @PutMapping
    @ApiOperation("Modify book")
    public ResponseEntity<?> modifyBook(@RequestBody Book b) {
        if(b.getId() == null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity!"), HttpStatus.BAD_REQUEST);
        } if(!bookService.existById(b.getId())) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Book with id: " + b.getId() + " doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookService.save(b), HttpStatus.OK);
    }
}
