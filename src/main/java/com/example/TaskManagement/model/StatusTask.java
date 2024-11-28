package com.example.TaskManagement.model;

import com.example.TaskManagement.model.enums.StatusTaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
@Entity
@Table(name = "status", schema = "task_schema")
@AllArgsConstructor
@NoArgsConstructor
public class StatusTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_task")
    private StatusTaskType status;
}
