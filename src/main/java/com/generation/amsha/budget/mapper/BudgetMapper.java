package com.generation.amsha.budget.mapper;

import com.generation.amsha.budget.dto.BudgetDto;
import com.generation.amsha.budget.model.Budget;
import com.generation.amsha.user.mapper.UserAccountMapper;

public class BudgetMapper {
    UserAccountMapper userAccountMapper = new UserAccountMapper();
    public BudgetDto budgetToBudgetDto(Budget budget) {
        return BudgetDto.builder()
                .budgetId(budget.getId())
                .name(budget.getName())
                .plannedAmount(budget.getPlannedAmount())
                .actualAmount(budget.getActualAmount())
                .createdAt(budget.getCreatedAt())
                .updatedAt(budget.getUpdatedAt())
                .archived(budget.getArchived())
                .user(userAccountMapper.toUserDto(budget.getUserAccount()))
                .build();
    }
}
