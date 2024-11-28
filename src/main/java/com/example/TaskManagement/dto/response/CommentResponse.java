package com.example.TaskManagement.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommentResponse {

    private String text;

    private String author;

    private LocalDateTime lastUpdate;


}
