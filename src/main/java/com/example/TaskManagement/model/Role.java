package com.example.TaskManagement.model;


import com.example.TaskManagement.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;


@Entity
@Table(name = "role", schema = "task_schema")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_user")
    private RoleType role;

    public SimpleGrantedAuthority toAuthority(){
        return new SimpleGrantedAuthority(role.name());
    }

}
