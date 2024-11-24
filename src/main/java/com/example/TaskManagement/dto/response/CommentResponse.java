package com.example.TaskManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentResponse {

    private String text;

    private String author;

    private LocalDateTime lastUpdate;


}
