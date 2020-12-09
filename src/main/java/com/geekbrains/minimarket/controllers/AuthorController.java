package com.geekbrains.minimarket.controllers;

import com.geekbrains.minimarket.dto.AuthorDto;
import com.geekbrains.minimarket.entites.Author;
import com.geekbrains.minimarket.exceptions.MarketError;
import com.geekbrains.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.minimarket.service.AuthorService;
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
@RequestMapping("/api/v1/authors")
@Api("Set of endpoints for authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Get all authors
     * Example: GET http://localhost:8189/market/api/v1/authors
     * */
    @GetMapping
    @ApiOperation("Return all authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors().stream().map(AuthorDto::new).collect(Collectors.toList());
    }

    /**
     * Get author by id
     * Example: GET http://localhost:8189/market/api/v1/authors/1
     * */
    @GetMapping("/{id}")
    @ApiOperation("Returns a specific author by their identifier. 404 if doesn't exist.")
    public AuthorDto getAuthorsById(@ApiParam("Id of the author to be obtained. Can not be empty.") @PathVariable Long id) {
        Author p = authorService.getOneById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find author with id: " + id));
        return new AuthorDto(p);
    }

    /**
     * Create new author
     * Example: POST http://localhost:8189/market/api/v1/authors
     * */
    @PostMapping
    @ApiOperation("Creates a new author. If id != null, then throw bad request response")
    public ResponseEntity<?> createNewAuthors(@RequestBody Author p) {
        if(p.getId() != null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authorService.save(p), HttpStatus.CREATED);
    }

    /**
     * Modify author
     * Example: PUT http://localhost:8189/market/api/v1/authors
     * */
    @PutMapping
    @ApiOperation("Modify author")
    public ResponseEntity<?> modifyAuthor(@RequestBody Author p) {
        if(p.getId() == null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity!"), HttpStatus.BAD_REQUEST);
        } if(!authorService.existById(p.getId())) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Author with id: " + p.getId() + " doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authorService.save(p), HttpStatus.OK);
    }
}
