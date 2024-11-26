package com.example.TaskManagement.service;

import com.example.TaskManagement.model.*;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса комментариев")
public class CommentServiceTest {

    private final CommentRepository commentRepository =
            Mockito.mock(CommentRepository.class);

    private final CommentServiceImpl commentService = new CommentServiceImpl(commentRepository);

    private User userOne;
    private User userTwo;
    private User userThree;
    private Task task;
    private Comment commentOne;
    private Comment commentTwo;
    private List<Comment> comments;

    @BeforeEach
    public void createVariables() {
        userOne = User.builder()
                .id(1L).name("SomeName").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some@some.some").roles(new Role(1L, RoleType.ADMIN))
                .tasks(null).build();
        userTwo = User.builder()
                .id(2L).name("SomeName2").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some2@some.some").roles(new Role(2L, RoleType.USER))
                .tasks(null).build();
        userThree = User.builder()
                .id(3L).name("SomeName3").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some3@some.some").roles(new Role(2L, RoleType.USER))
                .tasks(null).build();

        List<User> users = new ArrayList<>();
        users.add(userTwo);
        users.add(userThree);

        task = Task.builder().id(1L).title("some task").description("some description")
                .statusTask(new StatusTask(1L, StatusTaskType.IN_PROCESS))
                .priority(new Priority(1L, PriorityType.HIGH))
                .comments(null).author(userOne).executors(users).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();


        commentOne = Comment.builder().id(1L).text("Some text 1").author(userOne)
                .task(task).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();

        commentTwo = Comment.builder().id(2L).text("Some text 2").author(userTwo)
                .task(task).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();

        comments = new ArrayList<>();
        comments.add(commentOne);
        task.setComments(comments);
    }

    @Test
    @DisplayName("Поиск коментария по ID")
    public void testFindById() {
        Long id = 1L;
        when(commentRepository.findById(id))
                .thenReturn(Optional.ofNullable(commentOne));
        Comment comment = commentService.findById(id);
        assertEquals(comment.getId(), commentOne.getId());
        verify(commentRepository, times(1)).findById(id);

    }


    @Test
    @DisplayName("Поиск всех комментариев")
    public void testFindAll() {
        when(commentRepository.findAll()).thenReturn(comments);
        List<Comment> commentList = commentService.findAll();
        assertEquals(commentList.size(), comments.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Поис коментария по задаче")
    public void testFindAllByTask() {
        when(commentRepository.findAllByTask(task))
                .thenReturn(comments);
        List<Comment> commentList = commentService.findAllByTask(task);
        assertEquals(commentList.size(), comments.size());
        verify(commentRepository, times(1)).findAllByTask(task);
    }

    @Test
    @DisplayName("Поис коментария по автору")
    public void testFindAllByAuthor() {
        List<Comment> commentAuthor = new ArrayList<>();
        commentAuthor.add(commentOne);
        when(commentRepository.findAllByAuthor(userOne))
                .thenReturn(commentAuthor);
        List<Comment> commentList = commentService.findAllByAuthor(userOne);
        assertEquals(commentList.size(), commentAuthor.size());
        verify(commentRepository, times(1)).findAllByAuthor(userOne);
    }

    @Test
    @DisplayName("Сохранение коментария")
    public void testSaveComment() {
        Comment comment = new Comment();
        comment.builder().id(3L).text("Some text 3").author(userOne).task(task)
                .createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).build();

        when(commentRepository.save(comment)).thenReturn(comment);
        Comment commentSave = commentService.save(comment);
        assertEquals(commentSave, comment);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("Сохранение обновления коментария")
    public void testUpdateComment() {
        Comment comment = new Comment();
        comment.builder().id(2L).text("Some text Update").author(userOne).task(task)
                .createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).build();

        when(commentRepository.save(comment)).thenReturn(comment);
        Comment commentSave = commentService.update(comment);
        assertEquals(commentSave, comment);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    @DisplayName("Удаление соментария")
    public void testDeleteComment() {
        Long id = 1L;
        commentService.delete(id);
        verify(commentRepository, times(1)).deleteById(id);
    }

}
