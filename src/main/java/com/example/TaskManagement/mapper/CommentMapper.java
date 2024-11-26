package com.example.TaskManagement.mapper;


import com.example.TaskManagement.dto.request.CommentRequest;
import com.example.TaskManagement.dto.response.CommentResponse;
import com.example.TaskManagement.dto.response.list.CommentListResponse;
import com.example.TaskManagement.mapper.delegate.CommentDelegate;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentDelegate.class)
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {


    Comment commentRequestToComment(CommentRequest request);

    @Mapping(source = "id", target = "id")
    Comment commentRequestToComment(Long id, CommentRequest request);

    CommentResponse commentToCommentResponse(Comment comment);

    List<CommentResponse> commentListToListResponse(List<Comment> comments);

    default CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToListResponse(comments));
        return response;
    }

    default String map(User author) {
        return author.getName();
    }

}
