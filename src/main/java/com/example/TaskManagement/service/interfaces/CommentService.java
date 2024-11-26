package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.model.pagination.PageInfo;

import java.util.List;

public interface CommentService {

    Comment findById (Long id);

    List<Comment> findAll(PageInfo pageInfo);

    List<Comment> findAllByTask (Task task);

    List<Comment> findAllByTask (Task task, PageInfo pageInfo);

    List<Comment> findAllByAuthor(User author);

    List<Comment> findAllByAuthor(User author, PageInfo pageInfo);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void delete(Long id);
}
