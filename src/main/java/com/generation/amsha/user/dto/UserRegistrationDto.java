package com.generation.amsha.user.dto;

import com.generation.amsha.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDto {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String password;
    private Role role;
}
