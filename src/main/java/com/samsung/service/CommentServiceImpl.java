package com.samsung.service;

import com.samsung.dao.BookDao;
import com.samsung.dao.CommentDao;
import com.samsung.domain.Book;
import com.samsung.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final BookDao bookDao;

    @Override
    @Transactional
    public Comment insert(String content, int bookId) {

        Book book = bookDao.findById(bookId).orElse(null);

        Comment comment = Comment.builder()
                .content(content)
                .book(book)
                .build();

        return commentDao.save(comment);
    }

    @Override
    public List<Comment> getAll() {

        return commentDao.findAll();
    }

    @Override
    public Comment getById(int id) {

        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getByBookId(int id) {

        return commentDao.findByBookId(id);
    }

    @Override
    @Transactional
    public void update(int id, String content) {

        commentDao.updateContentById(id, content);
    }

    @Override
    @Transactional
    public void deleteById(int id) {

        commentDao.deleteById(id);
    }
}
