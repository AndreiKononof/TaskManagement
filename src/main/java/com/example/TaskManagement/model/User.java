package com.example.TaskManagement.model;


import com.example.TaskManagement.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
