package com.example.TaskManagement.service;

import com.example.TaskManagement.dto.filter.TaskFilter;
import com.example.TaskManagement.exception.ValidTaskException;
import com.example.TaskManagement.model.*;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.model.pagination.PageInfo;
import com.example.TaskManagement.repository.TaskRepository;
import com.example.TaskManagement.repository.specification.TaskSpecification;
import com.example.TaskManagement.service.interfaces.PriorityService;
import com.example.TaskManagement.service.interfaces.StatusTaskService;
import com.example.TaskManagement.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса задач")
public class TaskServiceTest {

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final StatusTaskService statusTaskService = Mockito.mock(StatusTaskService.class);
    private final PriorityService priorityService = Mockito.mock(PriorityService.class);
    private final UserService userService = Mockito.mock(UserService.class);

    private final TaskServiceImpl taskService = new TaskServiceImpl(taskRepository, statusTaskService, priorityService, userService);

    private Task task;
    private User userOne;
    private User userTwo;

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
        User userThree = User.builder()
                .id(3L).name("SomeName3").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some3@some.some").role(new Role(2L, RoleType.USER))
                .tasks(null).build();

        List<User> users = new ArrayList<>();
        users.add(userTwo);
        users.add(userThree);

        task = Task.builder().id(1L).title("some task").description("some description")
                .statusTask(new StatusTask(1L, StatusTaskType.WAITING))
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
        Task taskDB = taskService.findById(id);
        assertEquals(taskDB.getId(), task.getId());
        verify(taskRepository, times(1)).findById(id);

    }

    @ParameterizedTest(name = "Для пользователя {0}")
    @ValueSource(strings = {"SomeName2", "SomeName1"})
    @DisplayName("Поиск задачи по ID и имени пользователя")
    public void testFindByUserTask(String name) {
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        if (name.equals("SomeName2")) {
            Task taskDB = taskService.findByUser(1L, name);
            assertEquals(task.getId(), taskDB.getId());
            verify(taskRepository, times(1)).findById(1L);
        }
        if (name.equals("SomeName1")) {
            assertThrows(ValidTaskException.class, () -> taskService.findByUser(1L, name));
            verify(taskRepository,times(1)).findById(1L);
        }
    }

    @Test
    @DisplayName("Поиск всех задач")
    public void testFindByAllTask(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        PageInfo pageInfo = new PageInfo();
        when(taskRepository.findAll(PageRequest.of(pageInfo.getNumber(), pageInfo.getSize())))
                .thenReturn(new PageImpl<>(tasks));
        taskService.findAll(pageInfo);
        verify(taskRepository, times(1))
                .findAll(PageRequest.of(pageInfo.getNumber(), pageInfo.getSize()));
    }

    @ParameterizedTest(name = "Для пользователя {0}")
    @ValueSource(strings = {"SomeName2", "SomeName1"})
    @DisplayName("Поиск всех задач по ID и имени пользователя")
    public void testFindALLByUserTask(String name) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskRepository.findAll()).thenReturn(tasks);
        if (name.equals("SomeName2")) {
            when(userService.findByName(name)).thenReturn(userTwo);
            List<Task> tasksDB = taskService.findAllByUser(name, new PageInfo());
            assertEquals(1, tasksDB.size());
            verify(taskRepository, times(1)).findAll();
        }
        if (name.equals("SomeName1")) {
            when(userService.findByName(name)).thenReturn(userOne);
            List<Task> tasksDB = taskService.findAllByUser(name, new PageInfo());
            assertEquals(0, tasksDB.size());
            verify(taskRepository,times(1)).findAll();
        }
    }

    @Test
    @DisplayName("Поиск задач с фильтром")
    public void testFindByFilter(){
        TaskFilter filter = new TaskFilter();
        filter.setAuthorId(task.getAuthor().getId());
        filter.setExecutorId(task.getExecutors().get(0).getId());
        when(taskRepository.findAll(TaskSpecification.withFilter(filter))).thenReturn(List.of(task));
         taskService.findAllBy(filter);
        assertNotEquals(null,taskService.findAllBy(filter));
    }

    @Test
    @DisplayName("Сохранение задачи")
    public void testSaveTask(){
        when(taskRepository.save(task)).thenReturn(task);
        Task taskDB = taskService.save(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("Обновление задачи")
    public void testUpdateTask(){
        when(taskRepository.save(task)).thenReturn(task);
        Task taskDB = taskService.save(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    @DisplayName("Обновление статуса задачи")
    public void testUpdateStatusTaskWhenStatsFind(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        StatusTask statusTask = new StatusTask(1L, StatusTaskType.IN_PROCESS);
        when(statusTaskService.findByStatus(StatusTaskType.IN_PROCESS)).thenReturn(statusTask);
        Task taskDB = taskService.updateStatus(1L, StatusTaskType.IN_PROCESS);
        assertEquals(task.getStatusTask().getStatus(),taskDB.getStatusTask().getStatus());
        verify(statusTaskService,times(1)).findByStatus(StatusTaskType.IN_PROCESS);
        verify(statusTaskService,times(0)).save(statusTask);

    }
    
    @Test
    @DisplayName("Обновление статуса задачи статус не найден")
    public void testUpdateStatusTaskWhenStatsNotFind(){
        Long id = 1L;
        StatusTask statusTask = new StatusTask(1L, StatusTaskType.IN_PROCESS);

        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));
        when(statusTaskService.findByStatus(StatusTaskType.IN_PROCESS)).thenReturn(null);
        when(statusTaskService.save(Mockito.any())).thenReturn(statusTask);

        Task taskDB = taskService.updateStatus(id, StatusTaskType.IN_PROCESS);
        assertEquals(task.getStatusTask().getStatus(),taskDB.getStatusTask().getStatus());

        verify(taskRepository,times(1)).findById(id);
        verify(statusTaskService,times(1)).findByStatus(StatusTaskType.IN_PROCESS);
}

    @Test
    @DisplayName("Обновление приоритета задачи")
    public void testUpdatePriorityWhenPriorityFind(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        Priority priority = new Priority(1L, PriorityType.HIGH);

        when(priorityService.findByPriority(PriorityType.HIGH)).thenReturn(priority);
        Task taskDB = taskService.updatePriority(1L, PriorityType.HIGH);
        assertEquals(task.getPriority().getPriorityType(),taskDB.getPriority().getPriorityType());
        verify(priorityService,times(1)).findByPriority(PriorityType.HIGH);
        verify(priorityService,times(0)).save(priority);

    }

    @Test
    @DisplayName("Обновление приоритета задачи приоритет не найден")
    public void testUpdatePriorityWhenPriorityNotFind(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));

        Priority priority = new Priority(1L, PriorityType.HIGH);

        when(priorityService.findByPriority(PriorityType.HIGH)).thenReturn(null);
        when(priorityService.save(Mockito.any())).thenReturn(priority);

        Task taskDB = taskService.updatePriority(1L, PriorityType.HIGH);

        assertEquals(task.getPriority().getPriorityType(),taskDB.getPriority().getPriorityType());
        verify(priorityService,times(1)).findByPriority(PriorityType.HIGH);
        verify(priorityService,times(0)).save(priority);
    }

    @Test
    @DisplayName("Добавление исполнителя")
    public void testAddExecutor(){
        User user = User.builder()
                .name("new User")
                .password("1234")
                .email("some@Some.some")
                .role(new Role(1L, RoleType.ADMIN))
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .tasks(null)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(userService.findByName(Mockito.any())).thenReturn(user);
        Task taskDB = taskService.addExecutor(1L, user.getName());
        assertEquals(taskDB.getExecutors().size(), 3);
    }

    @Test
    @DisplayName("Удаление задачи")
    public void testDelete(){
        taskService.delete(1L);
        verify(taskRepository,times(1)).deleteById(1L);
    }

}
