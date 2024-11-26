package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("From User WHERE name = :name")
    Optional<User> findByName (@Param("name") String name);

}
