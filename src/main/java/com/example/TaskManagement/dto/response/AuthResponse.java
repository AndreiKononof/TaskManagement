package com.example.TaskManagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private Long id;

    private String token;

    private String refreshToken;

    private String userName;

    private String email;

    private String role;


}
