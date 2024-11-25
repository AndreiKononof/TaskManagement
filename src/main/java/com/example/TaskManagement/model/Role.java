package com.example.TaskManagement.model;


import com.example.TaskManagement.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "role")
@Data
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_user")
    private RoleType role;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
