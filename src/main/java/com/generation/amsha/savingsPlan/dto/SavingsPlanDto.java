package com.generation.amsha.savingsPlan.dto;

import com.generation.amsha.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsPlanDto {
    private Integer id;
    private String title;
    private String description;
    private Double currentAmount;
    private Double targetAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean archived;
    private UserDto user;
}
