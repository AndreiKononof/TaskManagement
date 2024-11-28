package com.example.TaskManagement.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

@RedisHash("refresh_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @Indexed
    private Long id;

    @Indexed
    private Long userId;

    @Indexed
    private String token;

    @Indexed
    private Instant expiryDate;


}
