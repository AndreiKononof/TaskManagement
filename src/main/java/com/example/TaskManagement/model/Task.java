package com.example.TaskManagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "task", schema = "task_schema")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "status_id")
    private StatusTask statusTask;

    @OneToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    @ToString.Exclude
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "task_executors",
            joinColumns = @JoinColumn(name = "task_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "executor_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<User> executors;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;


}
