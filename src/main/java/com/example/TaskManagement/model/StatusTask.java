package com.example.TaskManagement.model;

import com.example.TaskManagement.model.enums.StatusTaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Entity
@Table(name = "status", schema = "task_schema")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatusTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_task")
    private StatusTaskType status;
}
