package com.samsung.rest.controller;

import com.samsung.domain.Book;
import com.samsung.rest.dto.BookDto;
import com.samsung.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public BookDto createNewBook(
            @RequestParam String nameBook,
            @RequestParam String nameGenre,
            @RequestParam String nameAuthor
    ) {

        Book book = bookService.insert(nameBook, nameGenre, nameAuthor);
        return BookDto.toDto(book);
    }

    @GetMapping("/book")
    public List<BookDto> getAllBooks() {

        return bookService
                .getAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }


    @PostMapping("/book/{id}/")
    public BookDto updateBookById(
            @PathVariable int id,
            @RequestParam String newBookName,
            @RequestParam String newGenreName,
            @RequestParam String newAuthorName
    ) {

        Book book = bookService.update(
                id,
                newBookName,
                newGenreName,
                newAuthorName
        );

        return BookDto.toDto(book);
    }

    @GetMapping("/book/{id}")
    public BookDto getBookById(@PathVariable int id) {

        return BookDto.toDto(bookService.getById(id));
    }

    @GetMapping("/book/name")
    public BookDto getBookByName(@RequestParam String name) {

        return BookDto.toDto(bookService.getByName(name));
    }

    @DeleteMapping("/book/{id}")
    //@PostMapping("/deleteBookById")
    public void deleteBookById(@PathVariable int id) {

        bookService.deleteById(id);
    }

}
