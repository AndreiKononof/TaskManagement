package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.User;

import java.util.List;

public interface CommentService {

    Comment findById (Long id);

    List<Comment> findAll();

    List<Comment> findALLByAuthor(User author);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void delete(Long id);
}
