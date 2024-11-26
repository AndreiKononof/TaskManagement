package com.example.TaskManagement.service;

import com.example.TaskManagement.model.StatusTask;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.repository.StatusTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса статуса задач")
public class StatusTaskServiceTest {

    private final StatusTaskRepository repository = Mockito.mock(StatusTaskRepository.class);

    private final StatusTaskServiceImpl service = new StatusTaskServiceImpl(repository);

    List<StatusTask> statusTasks;

    @BeforeEach
    public void createVariable() {
        StatusTask statusTaskWaiting = new StatusTask(1L, StatusTaskType.WAITING);
        StatusTask statusTaskProcess = new StatusTask(2L, StatusTaskType.IN_PROCESS);
        StatusTask statusTaskExecuted = new StatusTask(3L, StatusTaskType.EXECUTED);

        statusTasks = new ArrayList<>();
        statusTasks.add(0, statusTaskWaiting);
        statusTasks.add(1, statusTaskProcess);
        statusTasks.add(4, statusTaskExecuted);
    }

    @Test
    @DisplayName("Поиск роли по ID")
    public void testFindByIdRole() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.ofNullable(statusTasks.get(0)));
        StatusTask statusTask = service.findById(id);
        assertEquals(statusTasks.get(0).getId(), statusTask.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Поиск роли по типу")
    public void testFindByStatus() {
        when(repository.findByStatusType(StatusTaskType.WAITING)).thenReturn(statusTasks.get(0));

        StatusTask statusTask = service.findByStatus(StatusTaskType.WAITING);

        assertEquals(StatusTaskType.WAITING, statusTask.getStatus());
        verify(repository, times(1)).findByStatusType(StatusTaskType.WAITING);
    }

    @Test
    @DisplayName("Сохранение роли")
    public void testSaveRole() {
        when(repository.save(statusTasks.get(0))).thenReturn(statusTasks.get(0));
        StatusTask statusTask = service.save(statusTasks.get(0));

        assertEquals(statusTasks.get(0).getId(), statusTask.getId());
        verify(repository, times(1)).save(statusTasks.get(0));
    }
}
