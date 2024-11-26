package com.example.TaskManagement.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users", schema = "task_schema")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToOne()
    @JoinColumn(name = "role_id")
    private Role roles;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "task_executors",
    joinColumns = @JoinColumn(name = "executor_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private List<Task> tasks;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
