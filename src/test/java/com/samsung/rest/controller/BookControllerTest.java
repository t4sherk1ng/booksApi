package com.samsung.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.domain.Author;
import com.samsung.domain.Book;
import com.samsung.domain.Comment;
import com.samsung.domain.Genre;
import com.samsung.rest.dto.BookDto;
import com.samsung.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    public static final int AUTHOR_ID1 = 1;
    public static final String AUTHOR_NAME_1 = "authorName1";
    public static final Author AUTHOR1 = new Author(AUTHOR_ID1, AUTHOR_NAME_1);
    public static final String GENRE_NAME_1 = "genreName1";
    public static final int GENRE_ID1 = 1;
    public static final Genre GENRE1 = new Genre(GENRE_ID1, GENRE_NAME_1);
    public static final Comment COMMENT1 = new Comment(1, "commentContent1", null);
    public static final List<Comment> COMMENTS = new ArrayList<>();
    public static final int BOOK_ID1 = 1;
    public static final String BOOK_NAME_1 = "bookName1";
    public static final Book BOOK1 = new Book(
            BOOK_ID1,
            BOOK_NAME_1,
            COMMENTS,
            AUTHOR1,
            GENRE1
    );

    public static final int BOOK_ID2 = 2;
    public static final String BOOK_NAME_2 = "bookName2";
    public static final Book BOOK2 = new Book(
            BOOK_ID2,
            BOOK_NAME_2,
            COMMENTS,
            AUTHOR1,
            GENRE1
    );

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @Test
    void shouldCorrectCreateNewBook() throws Exception {

        given(bookService.insert(BOOK_NAME_1, GENRE_NAME_1, AUTHOR_NAME_1)).willReturn(BOOK1);

        BookDto expectedResult = BookDto.toDto(BOOK1);

        mvc.perform(post("/book")
                        .param("nameBook", BOOK_NAME_1)
                        .param("nameGenre", GENRE_NAME_1)
                        .param("nameAuthor", AUTHOR_NAME_1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectGetAllBooks() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(BOOK1);
        books.add(BOOK2);

        given(bookService.getAll()).willReturn(books);

        List<BookDto> expectedResult = books.stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));

    }

    @Test
    void shouldCorrectUpdateBookById() throws Exception {

        given(bookService.update(BOOK_ID1, BOOK_NAME_1, GENRE_NAME_1, AUTHOR_NAME_1)).willReturn(BOOK1);

        BookDto expectedResult = BookDto.toDto(BOOK1);

        mvc.perform(post("/book/1/")
                        .param("newBookName", BOOK_NAME_1)
                        .param("newGenreName", GENRE_NAME_1)
                        .param("newAuthorName", AUTHOR_NAME_1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));

    }

    @Test
    void shouldCorrectGetBookById() throws Exception {

        given(bookService.getById(BOOK_ID1)).willReturn(BOOK1);

        BookDto expectedResult = BookDto.toDto(BOOK1);

        mvc.perform(get("/book/" + BOOK_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectGetBookByName() throws Exception {

        given(bookService.getByName(BOOK_NAME_1)).willReturn(BOOK1);

        BookDto expectedResult = BookDto.toDto(BOOK1);

        mvc.perform(get("/book/name")
                        .param("name", String.valueOf(BOOK_NAME_1)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectDeleteBookById() throws Exception {

        mvc.perform(delete("/book/" + BOOK_ID1))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteById(1);
    }
}