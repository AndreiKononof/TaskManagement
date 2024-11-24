package com.example.TaskManagement.model;

import com.example.TaskManagement.model.enums.Priority;
import com.example.TaskManagement.model.enums.StatusTask;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTask statusTask;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @CreationTimestamp
    @Column(name = "create")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update")
    private LocalDateTime updateTime;


}
