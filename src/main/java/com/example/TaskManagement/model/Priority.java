package com.example.TaskManagement.model;


import com.example.TaskManagement.model.enums.PriorityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@ToString
@Table(name = "priority", schema = "task_schema")
@AllArgsConstructor
@NoArgsConstructor
public class Priority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_task")
    private PriorityType priorityType;

}
