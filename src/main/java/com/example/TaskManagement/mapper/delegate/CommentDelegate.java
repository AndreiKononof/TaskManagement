package com.example.TaskManagement.mapper.delegate;

import com.example.TaskManagement.dto.request.CommentRequest;
import com.example.TaskManagement.dto.response.CommentResponse;
import com.example.TaskManagement.exception.UserNotAllowedCorrectException;
import com.example.TaskManagement.mapper.CommentMapper;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class CommentDelegate implements CommentMapper {

    private final UserService userService;

    private final CommentService commentService;

    @Override
    public Comment commentRequestToComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setAuthor(userService.findByName(request.getUser()));
        comment.setText(request.getText());
        return comment;
    }

    @Override
    public Comment commentRequestToComment(Long id, CommentRequest request) {
        Comment comment = commentService.findById(id);
        if(!comment.getAuthor().getName().equals(request.getUser())) {
            throw new UserNotAllowedCorrectException("Корректировка коментария не возможна");
        }
        comment.setText(request.getText());
        return comment;
    }

    @Override
    public CommentResponse commentToCommentResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setText(comment.getText());
        response.setAuthor(userService.findById(comment.getAuthor().getId()).getName());
        LocalDateTime lastUpdate = comment.getUpdateTime();
        if(lastUpdate == null){
           lastUpdate = comment.getCreateTime();
        }
        response.setLastUpdate(lastUpdate);
        return response;
    }
}
