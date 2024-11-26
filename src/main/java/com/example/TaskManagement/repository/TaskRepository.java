package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {

}
