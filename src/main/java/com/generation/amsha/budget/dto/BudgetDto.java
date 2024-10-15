package com.generation.amsha.budget.dto;

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
public class BudgetDto {
    private Integer budgetId;
    private String name;
    private Double plannedAmount;
    private Double actualAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean archived;
    private UserDto user;
}
