package com.geekbrains.minimarket.dto;

import com.geekbrains.minimarket.entites.Book;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "Book dto in the application.")
public class BookDto {

    @ApiModelProperty(notes = "Unique identifier of the book. No two books can have the same id.", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "Title of the book.", example = "After quarrel", required = true, position = 1)
    private String title;

    @ApiModelProperty(notes = "Author name.", example = "Anna Todd", required = true, position = 3)
    private String author;

    public BookDto(Book c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.author = c.getAuthor().getName();

    }
}
