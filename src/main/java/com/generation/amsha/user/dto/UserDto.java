package com.generation.amsha.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime archivedAt;
    private Integer blogs;
    private Integer comments;
    private Boolean archived;
}
