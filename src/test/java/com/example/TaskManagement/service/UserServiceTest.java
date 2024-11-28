package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.repository.UserRepository;
import com.example.TaskManagement.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Тесты сервиса пользователь")
public class UserServiceTest {

    private final UserRepository repository = Mockito.mock(UserRepository.class);

    private final UserService service = new UserServiceImpl(repository);

    private User user;

    @BeforeEach
    public void createVariable() {
        user = User.builder()
                .id(1L).name("SomeName").password("12345")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .email("Some@some.some").role(new Role(1L, RoleType.ADMIN))
                .tasks(null).build();
    }

    @ParameterizedTest(name = "Пользователь с именем {0}")
    @ValueSource(strings = {"nameOne", "nameTwo"})
    @DisplayName("Поиск по имени")
    public void testFindByName(String name) {
        if (name.equals("nameOne")) {
            when(repository.findByName(name)).thenReturn(Optional.ofNullable(user));
            service.findByName(name);
            verify(repository, times(1)).findByName(name);
        } else {
            assertThrows(NotFoundException.class, () -> service.findByName(name));
        }
    }

    @Test
    @DisplayName("Поиск по ID")
    public void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(user));
        service.findById(1L);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Поиск всех пользователей")
    public void testFindAll() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        service.findAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Сохранение пользователя")
    public void testSave() {
        when(repository.save(user)).thenReturn(user);
        service.save(user);
        verify(repository, times(1)).save(user);
    }

    @Test
    @DisplayName("Обновление пользователя")
    public void testUpdate() {
        when(repository.save(user)).thenReturn(user);
        service.save(user);
        verify(repository, times(1)).save(user);
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void tetDelete() {
        service.delete(1l);
        verify(repository, times(1)).deleteById(1L);
    }


}
