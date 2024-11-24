package com.example.TaskManagement.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "role")
@Data
@ToString
public class Role {

    private Long id;

    private Role role;
}
