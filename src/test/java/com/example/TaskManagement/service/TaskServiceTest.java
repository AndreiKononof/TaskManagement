package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.ValidTaskException;
import com.example.TaskManagement.model.*;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.repository.TaskRepository;
import com.example.TaskManagement.service.interfaces.PriorityService;
import com.example.TaskManagement.service.interfaces.StatusTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса задач")
public class TaskServiceTest {

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final StatusTaskService statusTaskService = Mockito.mock(StatusTaskService.class);
    private final PriorityService priorityService = Mockito.mock(PriorityService.class);

    private final TaskServiceImpl service = new TaskServiceImpl(taskRepository, statusTaskService, priorityService);

    private Task task;

    @BeforeEach
    public void createVariable() {
        User userOne = User.builder()
                .id(1L).name("SomeName").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some@some.some").roles(new Role(1l, RoleType.ADMIN))
                .tasks(null).build();
        User userTwo = User.builder()
                .id(2L).name("SomeName2").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some2@some.some").roles(new Role(1l, RoleType.USER))
                .tasks(null).build();
        User userThree = User.builder()
                .id(3L).name("SomeName3").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some3@some.some").roles(new Role(1l, RoleType.USER))
                .tasks(null).build();

        List<User> users = new ArrayList<>();
        users.add(userTwo);
        users.add(userThree);

        task = Task.builder().id(1L).title("some task").description("some description")
                .statusTask(new StatusTask(1L, StatusTaskType.IN_PROCESS))
                .priority(new Priority(1L, PriorityType.HIGH))
                .comments(null).author(userOne).executors(users).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();


        Comment commentOne = Comment.builder().id(1L).text("Some text 1").author(userOne)
                .task(task).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();

        Comment commentTwo = Comment.builder().id(2L).text("Some text 2").author(userTwo)
                .task(task).createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now()).build();

        List<Comment> comments = new ArrayList<>();
        comments.add(commentOne);
        comments.add(commentTwo);
        task.setComments(comments);

    }

    @Test
    @DisplayName("Поиск задачи по ID")
    public void testFindByIdTask() {
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));
        Task taskDB = service.findById(id);
        assertEquals(taskDB.getId(), taskDB.getId());
        verify(taskRepository, times(1)).findById(id);

    }

    @ParameterizedTest(name = "Для пользователя {0}")
    @ValueSource(strings = {"SomeName2", "SomeName1"})
    @DisplayName("Поиск задачи по ID и имени пользователя")
    public void testFindByUserTask(String name) {
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        if (name.equals("SomeName2")) {
            Task taskDB = service.findByUser(1L, name);
            assertEquals(task.getId(), taskDB.getId());
            verify(taskRepository, times(1)).findById(1L);
        }
        if (name.equals("SomeName1")) {
            assertThrows(ValidTaskException.class, () -> service.findByUser(1L, name));
            verify(taskRepository,times(1)).findById(1L);
        }
    }

    @Test
    @DisplayName("Поиск всех задач")
    public void testFindByAllTask(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> taskList = service.findAll();
        verify(taskRepository, times(1)).findAll();
    }

    @ParameterizedTest(name = "Для пользователя {0}")
    @ValueSource(strings = {"SomeName2", "SomeName1"})
    @DisplayName("Поиск всех задач по ID и имени пользователя")
    public void testFindALLByUserTask(String name) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskRepository.findAll()).thenReturn(tasks);
        if (name.equals("SomeName2")) {
            List<Task> tasksDB = service.findAllByUser(name);
            assertEquals(1, tasksDB.size());
            verify(taskRepository, times(1)).findAll();
        }
        if (name.equals("SomeName1")) {
            List<Task> tasksDB = service.findAllByUser(name);
            assertEquals(0, tasksDB.size());
            verify(taskRepository,times(1)).findAll();
        }
    }

    @Test
    @DisplayName("Сохранение задачи")
    public void testSaveTask(){
        when(taskRepository.save(task)).thenReturn(task);
        Task taskDB = service.save(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("Обновление задачи")
    public void testUpdateTask(){
        when(taskRepository.save(task)).thenReturn(task);
        Task taskDB = service.save(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("Обновление статуса задачи статус найден")
    public void testUpdateStatusTaskWhenStatsFind(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        StatusTask statusTask = new StatusTask(1L, StatusTaskType.IN_PROCESS);
        when(statusTaskService.findByStatus(StatusTaskType.IN_PROCESS)).thenReturn(statusTask);
        Task taskDB = service.updateStatus(1L, StatusTaskType.IN_PROCESS);
        verify(statusTaskService,times(1)).findByStatus(StatusTaskType.IN_PROCESS);
        verify(statusTaskService,times(0)).save(statusTask);

    }
    @Test
    @DisplayName("Обновление статуса задачи статус не найден")
    public void testUpdateStatusTaskWhenStatsNotFind(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        StatusTask statusTask = new StatusTask(1L, StatusTaskType.IN_PROCESS);

        when(statusTaskService.findByStatus(statusTask.getStatus())).thenReturn(null);
        when(statusTaskService.save(statusTask)).thenReturn(statusTask);

        Task taskDBTwo = service.updateStatus(1L, StatusTaskType.IN_PROCESS);
        verify(taskRepository,times(1)).findById(1L);
        verify(statusTaskService,times(1)).findByStatus(StatusTaskType.IN_PROCESS);
        verify(statusTaskService,times(1)).save(statusTask);
        verify(service,times(1)).save(taskDBTwo);
}

}
