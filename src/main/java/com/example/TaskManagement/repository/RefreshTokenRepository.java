package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(Long userId);

}
