package com.generation.amsha.budget.dao;

import com.generation.amsha.budget.model.Budget;
import java.util.List;

public interface BudgetDao {
    Budget saveBudget(Budget budget);
    Budget updateBudget(Budget budget);

    Budget getBudgetByBudgetId(Integer budgetId);

    List<Budget> getUserBudgets(Integer userId);
    List<Budget> getAllBudgets();

}
