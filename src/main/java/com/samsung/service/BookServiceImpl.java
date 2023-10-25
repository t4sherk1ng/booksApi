package com.samsung.service;

import com.samsung.dao.AuthorDao;
import com.samsung.dao.BookDao;
import com.samsung.dao.GenreDao;
import com.samsung.domain.Author;
import com.samsung.domain.Book;
import com.samsung.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final GenreDao genreDao;
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    @Transactional
    public Book insert(String nameBook,
                       String nameGenre,
                       String nameAuthor) {


        Author author = authorDao.findByName(nameAuthor);
        if (author == null) {
            author = Author.builder()
                    .name(nameAuthor)
                    .build();
        }

        Genre genre = genreDao.findByName(nameGenre);
        if (genre == null) {
            genre = Genre.builder()
                    .name(nameGenre)
                    .build();
        }

        Book book = Book.builder()
                .name(nameBook)
                .author(author)
                .genre(genre)
                .build();

        return bookDao.save(book);
    }


    @Override
    @Transactional
    public Book update(int id,
                       String nameBook,
                       String nameGenre,
                       String nameAuthor) {

        Book book = Book.builder()
                .id(id)
                .name(nameBook)
                .author(authorService.getByName(nameAuthor))
                .genre(genreService.getByName(nameGenre))
                .build();

        return bookDao.save(book);
    }

    @Override
    public List<Book> getAll() {

        return bookDao.findAll();
    }

    @Override
    public Book getById(int id) {

        return bookDao.getById(id);
    }

    @Override
    public Book getByName(String name) {

        return bookDao.findByName(name);
    }

    @Transactional
    @Override
    public void deleteById(int id) {

        bookDao.deleteById(id);
    }
}
