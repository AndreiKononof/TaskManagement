package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.repository.CommentRepository;
import com.example.TaskManagement.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundException(MessageFormat.format("Комментарий с ID {} не найден", id)));
    log.info("Completed method findById comment ID {}",id);
        return comment;
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = commentRepository.findAll();
        log.info("Completed method findAll comment");
        return comments;
    }

    @Override
    public List<Comment> findALLByAuthor(User author) {
        List<Comment> comments = commentRepository.findAllByAuthor(author);
        log.info("Completed method findAll comment by author name - {}, ID -{}",
                author.getName(), author.getId());
        return List.of();
    }

    @Override
    public Comment save(Comment comment) {
        Comment commentSave = commentRepository.save(comment);
        log.info("Save comment ID - {}",comment.getId());
        return commentSave;
    }

    @Override
    public Comment update(Comment comment) {
        Comment commentUpdate = commentRepository.save(comment);
        log.info("Update comment ID {}",commentUpdate.getId());
        return commentUpdate;
    }

    @Override
    public void delete(Long id) {
    commentRepository.deleteById(id);
    log.info("Delete comment ID {}", id);
    }
}
