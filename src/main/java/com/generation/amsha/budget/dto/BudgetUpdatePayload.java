package com.generation.amsha.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetUpdatePayload {
    private Integer budgetId;
    private String name;
    private Double plannedAmount;
    private Double actualAmount;
}
