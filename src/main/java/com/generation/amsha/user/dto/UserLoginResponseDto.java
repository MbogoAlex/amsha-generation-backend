package com.generation.amsha.user.dto;

import com.generation.amsha.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponseDto {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime archivedAt;
    private Role role;
    private Double accountBalance;
    private String token;
}
