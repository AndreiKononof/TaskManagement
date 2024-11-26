package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.Priority;
import com.example.TaskManagement.model.enums.PriorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    @Query("From Priority where priorityType = :priority")
    Priority findByPriorityType(@Param("priority") PriorityType priorityType);
}
