package com.samsung.rest.dto;

import com.samsung.domain.Book;
import com.samsung.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private int id;

    private String name;

    private AuthorDto authorDto;

    private GenreDto genreDto;

    private List<CommentDto> commentDtoList;

    public static Book toDomainObject(BookDto bookDto, List<Comment> comments) {

        return new Book(
                bookDto.getId(),
                bookDto.getName(),
                comments,
                AuthorDto.toDomainObject(bookDto.getAuthorDto()),
                GenreDto.toDomainObject(bookDto.getGenreDto())
        );
    }

    public static BookDto toDto(Book book) {

        List<CommentDto> commentDtoList;
        if (book.getComments() != null) {
            commentDtoList = book.getComments().stream().map(CommentDto::toDto).collect(Collectors.toList());
        } else {
            commentDtoList = new ArrayList<>();
        }
        return new BookDto(
                book.getId(),
                book.getName(),
                AuthorDto.toDto(book.getAuthor()),
                GenreDto.toDto(book.getGenre()),
                commentDtoList
        );

    }
}
