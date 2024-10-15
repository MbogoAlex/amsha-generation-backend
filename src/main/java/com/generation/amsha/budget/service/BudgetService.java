package com.generation.amsha.budget.service;

import com.generation.amsha.budget.dto.BudgetDto;
import com.generation.amsha.budget.dto.BudgetPayload;
import com.generation.amsha.budget.dto.BudgetUpdatePayload;
import com.generation.amsha.budget.model.Budget;

import java.util.List;

public interface BudgetService {
    BudgetDto saveBudget(BudgetPayload budget);
    BudgetDto updateBudget(BudgetUpdatePayload budget);

    BudgetDto getBudgetByBudgetId(Integer budgetId);

    List<BudgetDto> getUserBudgets(Integer userId);
    List<BudgetDto> getAllBudgets();

    String archiveBudget(Integer budgetId);
}
