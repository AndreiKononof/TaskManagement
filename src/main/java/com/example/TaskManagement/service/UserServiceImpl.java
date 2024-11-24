package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.CreateUserException;
import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.repository.UserRepository;
import com.example.TaskManagement.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User findByName(String name) {
        User user = userRepository.findByName(name);
        if(user == null){
            throw new NotFoundException(MessageFormat.format( "Пользователь с именем {} не найден", name));
        }
        log.info("Completed method findByName user name - {}", name );
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundException(MessageFormat.format("Пользователь с ID - {} не найден", id)));
        log.info("Completed method findById user ID - {}",id);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        log.info("Completed method findAll users");
        return users;
    }

    @Override
    public User save(User newUser) {
        User user = userRepository.findByName(newUser.getName());
        if(user != null){
            throw  new CreateUserException(MessageFormat.format("Пользователь с таким именем уже зарегестрирован {}", newUser.getName()));
        }
        User userSave = userRepository.save(user);
        log.info("Save user ID - {}", userSave.getId());
        return userSave;
    }

    @Override
    public User update(User user) {
        User userUpdate = userRepository.save(user);
        log.info("Update user ID - {}",userUpdate.getId());
        return userUpdate;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("Delete user ID - {}",id);
    }
}
