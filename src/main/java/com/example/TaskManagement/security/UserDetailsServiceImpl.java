package com.example.TaskManagement.security;

import com.example.TaskManagement.model.User;
import com.example.TaskManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
          User user =  userRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден "+name));
        return new AppUserDetails(user);
    }
}
