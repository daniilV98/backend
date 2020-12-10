package com.geekbrains.minimarket.controllers;

import com.geekbrains.minimarket.dto.AuthorDto;
import com.geekbrains.minimarket.entites.Author;
import com.geekbrains.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.minimarket.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Api("Set of endpoints for authors")
public class AuthorController {

    private final AuthorService authorService;

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
}
