package com.example.TaskManagement.model;

import com.example.TaskManagement.model.enums.StatusTaskType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "status")
@ToString
public class StatusTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_task")
    private StatusTaskType status;

}
