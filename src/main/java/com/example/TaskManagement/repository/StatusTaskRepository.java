package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.StatusTask;
import com.example.TaskManagement.model.enums.StatusTaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface StatusTaskRepository extends JpaRepository<StatusTask, Long> {


    @Query("From StatusTask where status = :status")
    StatusTask findByStatusType(@Param("status") StatusTaskType status);
}
