package com.example.TaskManagement.model;


import com.example.TaskManagement.model.enums.PriorityType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "priority")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_task")
    private PriorityType priority;

}
