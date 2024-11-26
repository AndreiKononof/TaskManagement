package com.example.TaskManagement.mapper.delegate;

import com.example.TaskManagement.dto.request.CommentRequest;
import com.example.TaskManagement.dto.response.CommentResponse;
import com.example.TaskManagement.exception.UserNotAllowedCorrectException;
import com.example.TaskManagement.mapper.CommentMapper;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.TaskService;
import com.example.TaskManagement.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class CommentDelegate implements CommentMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;

    @Override
    public Comment commentRequestToComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setTask(taskService.findById(request.getTaskId()));
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
        response.setAuthor(comment.getAuthor().getName());
        LocalDateTime lastUpdate = comment.getUpdateTime();
        if(lastUpdate == null){
           lastUpdate = comment.getCreateTime();
        }
        response.setLastUpdate(lastUpdate);
        return response;
    }

    @Override
    public List<CommentResponse> commentListToListResponse(List<Comment> comments) {
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : comments){
            CommentResponse commentResponse = commentToCommentResponse(comment);
            responses.add(commentResponse);
        }
        return responses;
    }
}
