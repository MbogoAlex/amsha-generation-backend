package com.generation.amsha.budget.controller;

import com.generation.amsha.budget.dto.BudgetDto;
import com.generation.amsha.budget.dto.BudgetPayload;
import com.generation.amsha.budget.dto.BudgetUpdatePayload;
import com.generation.amsha.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BudgetController {
    ResponseEntity<Response> saveBudget(BudgetPayload budget);
    ResponseEntity<Response> updateBudget(BudgetUpdatePayload budget);

    ResponseEntity<Response> getBudgetByBudgetId(Integer budgetId);

    ResponseEntity<Response> getUserBudgets(Integer userId);
    ResponseEntity<Response> getAllBudgets();

    ResponseEntity<Response> archiveBudget(Integer budgetId);
}
