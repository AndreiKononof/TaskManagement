package com.example.TaskManagement.service;

import com.example.TaskManagement.model.Priority;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.repository.PriorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса приоритета")
public class PriorityServiceTest {

    private final PriorityRepository priorityRepository =
            Mockito.mock(PriorityRepository.class);

    private final PriorityServiceImpl priorityService = new PriorityServiceImpl(priorityRepository);


    private List<Priority> priorities;

    @BeforeEach
    public void createVariable() {
        Priority priorityLow = new Priority(1L, PriorityType.LOW);
        Priority priorityMedium = new Priority(2L, PriorityType.MEDIUM);
        Priority priorityHigh = new Priority(3L, PriorityType.HIGH);

        priorities = new ArrayList<>();
        priorities.add(0, priorityLow);
        priorities.add(1, priorityMedium);
        priorities.add(2, priorityHigh);
    }

    @Test
    @DisplayName("Поиск приоритета по ID")
    public void testFindById() {
        Long id = 1L;
        Priority priority = new Priority(1L, PriorityType.LOW);
        when(priorityRepository.findById(id)).thenReturn(Optional.ofNullable(priorities.get(0)));
        Priority priorityDB = priorityService.findById(id);
        assertEquals(priority.getId(), priorityDB.getId());
        verify(priorityRepository, times(1)).findById(id);


    }

    @Test
    @DisplayName("Поиск приоритета по типу")
    public void testFindByPriority() {
        Priority priorityLow = priorities.get(0);
        when(priorityRepository.findByPriorityType(priorityLow.getPriorityType())).thenReturn(priorities.get(0));
        Priority priority = priorityService.findByPriority(PriorityType.LOW);
        assertEquals(priority, priorityLow);
        verify(priorityRepository, times(1)).findByPriorityType(PriorityType.LOW);
    }

    @Test
    @DisplayName("Сохранение приоритета")
    public void testSavePriority() {
        Priority priorityTest = new Priority(1L, PriorityType.LOW);

        when(priorityRepository.save(priorityTest))
                .thenReturn(new Priority(1L, PriorityType.LOW));

        Priority priority = priorityService.save(priorityTest);
        assertEquals(new Priority(1L, PriorityType.LOW), priority);
        verify(priorityRepository, times(1)).save(priorityTest);
    }


}
