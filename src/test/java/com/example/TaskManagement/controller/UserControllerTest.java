package com.example.TaskManagement.controller;

import com.example.TaskManagement.controller.utils.JsonUtilClass;
import com.example.TaskManagement.dto.response.CommentResponse;
import com.example.TaskManagement.dto.response.TaskResponse;
import com.example.TaskManagement.dto.response.list.CommentListResponse;
import com.example.TaskManagement.mapper.TaskMapper;
import com.example.TaskManagement.model.*;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.service.interfaces.TaskService;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AdminControllerTest {

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private TaskMapper taskMapper;


    private Task taskOne;
    private Task taskTwo;
    private User userOne;
    private User userTwo;
    private User userThree;
    private Comment commentOne;
    private Comment commentTwo;
    private Comment commentThree;
    private Comment commentFour;
    private List<Comment> commentsOne;
    private List<Comment> commentsTwo;


    @BeforeEach
    public void createVariable() {
        userOne = User.builder()
                .id(1L).name("SomeName").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some@some.some").role(new Role(1L, RoleType.ADMIN))
                .tasks(null).build();
        userTwo = User.builder()
                .id(2L).name("SomeName2").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some2@some.some").role(new Role(2L, RoleType.USER))
                .tasks(null).build();
        userThree = User.builder()
                .id(3L).name("SomeName3").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some3@some.some").role(new Role(2L, RoleType.USER))
                .tasks(null).build();

        List<User> usersOne = List.of(userTwo, userThree);
        List<User> usersTwo = List.of(userThree);

        taskOne = Task.builder().id(1L).title("some task").description("some description")
                .statusTask(new StatusTask(1L, StatusTaskType.WAITING))
                .priority(new Priority(1L, PriorityType.HIGH))
                .comments(null).author(userOne).executors(usersOne).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();

        taskTwo = Task.builder().id(1L).title("some task two").description("some description two")
                .statusTask(new StatusTask(1L, StatusTaskType.IN_PROCESS))
                .priority(new Priority(1L, PriorityType.LOW))
                .comments(null).author(userTwo).executors(usersTwo).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();

        commentOne = Comment.builder().id(1L).text("Some text 1").author(userOne)
                .task(taskOne).createTime(null)
                .updateTime(LocalDateTime.now()).build();

        commentTwo = Comment.builder().id(2L).text("Some text 2").author(userTwo)
                .task(taskOne).createTime(null)
                .updateTime(LocalDateTime.now()).build();

        commentThree = Comment.builder().id(3L).text("Some text 3").author(userTwo)
                .task(taskOne).createTime(null)
                .updateTime(LocalDateTime.now()).build();

        commentFour = Comment.builder().id(4L).text("Some text 4").author(userThree)
                .task(taskOne).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();

        commentsOne = List.of(commentOne, commentTwo);

        commentsTwo = List.of(commentThree, commentFour);

        taskOne.setComments(commentsOne);
        taskTwo.setComments(commentsTwo);
    }


    @Test
    @DisplayName("Запрос задачи по ID")
    public void testGetTaskById() throws Exception {

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : taskOne.getComments()) {
            CommentResponse commentResponse = CommentResponse
                    .builder()
                    .text(comment.getText())
                    .author(comment.getAuthor().getName())
                    .lastUpdate(comment.getUpdateTime())
                    .build();

            commentResponses.add(commentResponse);
        }
        CommentListResponse commentListResponse = new CommentListResponse();
        commentListResponse.setComments(commentResponses);

        List<String> executors = new ArrayList<>();
        for (User executor : taskOne.getExecutors()) {
            executors.add(executor.getName());
        }

        TaskResponse taskResponse = TaskResponse
                .builder()
                .id(taskOne.getId())
                .title(taskOne.getTitle())
                .description(taskOne.getDescription())
                .status(taskOne.getStatusTask().getStatus())
                .priority(taskOne.getPriority().getPriorityType())
                .comments(commentListResponse)
                .nameAuthor(taskOne.getAuthor().getName())
                .nameExecutor(executors)
                .build();

        when(taskService.findById(1L)).thenReturn(taskOne);
        when(taskMapper.taskToTaskResponse(taskOne)).thenReturn(taskResponse);

        String response = mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = JsonUtilClass.readStringFromFile("response/find_task_by_id.json");

        verify(taskService,times(1)).findById(1L);
        verify(taskMapper, times(1)).taskToTaskResponse(taskOne);

        JsonAssert.assertJsonEquals(expected,response);
    }

}
