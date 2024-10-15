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
public class UserDto {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String profilePic;
    private LocalDateTime createdAt;
    private LocalDateTime archivedAt;
    private Role role;
    private Integer blogs;
    private Integer comments;
    private Integer transactions;
    private Boolean archived;
}
