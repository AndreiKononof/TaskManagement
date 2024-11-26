package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса Ролей")
public class RoleServiceTest {

    private final RoleRepository repository = Mockito.mock(RoleRepository.class);

    private final RoleServiceImpl service = new RoleServiceImpl(repository);

    List<Role> roles;

    @BeforeEach
    public void createVariable() {
        Role roleUser = new Role(1L, RoleType.USER);
        Role roleAdmin = new Role(2L, RoleType.ADMIN);
        roles = new ArrayList<>();
        roles.add(0, roleUser);
        roles.add(1, roleAdmin);
    }


    @Test
    @DisplayName("Поиск роли по ID")
    public void testFindByIdRole() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.ofNullable(roles.get(0)));
        Role role = service.findById(id);
        assertEquals(roles.get(0).getId(), role.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Поиск роли по типу")
    public void testFindByRole() {
        when(repository.findByRole(RoleType.USER)).thenReturn(roles.get(0));

        Role role = service.findByRole(RoleType.USER);

        assertEquals(RoleType.USER, role.getRole());
        verify(repository, times(1)).findByRole(RoleType.USER);
    }

    @Test
    @DisplayName("Сохранение роли")
    public void testSaveRole() {
        when(repository.save(roles.get(0))).thenReturn(roles.get(0));
        Role role = service.save(roles.get(0));

        assertEquals(roles.get(0).getId(), role.getId());
        verify(repository, times(1)).save(roles.get(0));
    }

}
