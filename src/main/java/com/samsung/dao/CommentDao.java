package com.samsung.dao;

import com.samsung.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Integer> {

    @Modifying
    @Query("update Comment c set c.content = :content where c.id = :id")
    void updateContentById(@Param("id") int id,
                           @Param("content") String content);

    List<Comment> findByBookId(int id);

}
