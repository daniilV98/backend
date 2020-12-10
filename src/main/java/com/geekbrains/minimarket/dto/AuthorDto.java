package com.geekbrains.minimarket.dto;

import com.geekbrains.minimarket.entites.Author;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ApiModel(description = "Author dto in the application.")
public class AuthorDto {

    @ApiModelProperty(notes = "Unique identifier of the author. No two authors can have the same id.", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "Name of the author.", example = "Anna Todd", required = true, position = 1)
    private String name;

    @ApiModelProperty(notes = "Books count of the author.", example = "5", required = true, position = 2)
    private Integer booksCount;

    private List<BookDto> books;

    public AuthorDto(Author b) {
        this.id = b.getId();
        this.name = b.getName();
        this.booksCount = b.getBooks().size();
        this.books = b.getBooks().stream().map(BookDto::new).collect(Collectors.toList());
    }


}
