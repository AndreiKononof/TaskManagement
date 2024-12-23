package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {


    Page<Comment> findAllByAuthor(User author, Pageable pageable);

    List<Comment> findAllByAuthor(User author);

    Page<Comment> findAllByTask(Task task, Pageable pageable);

    List<Comment> findAllByTask(Task task);
}
