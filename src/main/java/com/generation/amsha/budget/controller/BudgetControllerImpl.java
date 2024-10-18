package com.generation.amsha.budget.controller;

import com.generation.amsha.budget.dto.BudgetPayload;
import com.generation.amsha.budget.dto.BudgetUpdatePayload;
import com.generation.amsha.budget.service.BudgetService;
import com.generation.amsha.response.BuildResponse;
import com.generation.amsha.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/api/")
public class BudgetControllerImpl implements BudgetController{
    BuildResponse buildResponse = new BuildResponse();

    private final BudgetService budgetService;
    @Autowired
    public BudgetControllerImpl(
            BudgetService budgetService
    ) {
        this.budgetService = budgetService;
    }
    @PostMapping("budget")
    @Override
    public ResponseEntity<Response> saveBudget(@RequestBody BudgetPayload budget) {
        return buildResponse.buildResponse("budget", budgetService.saveBudget(budget), "Budget saved", HttpStatus.OK);
    }
    @PutMapping("budget")
    @Override
    public ResponseEntity<Response> updateBudget(@RequestBody BudgetUpdatePayload budget) {
        return buildResponse.buildResponse("budget", budgetService.updateBudget(budget), "Budget updated", HttpStatus.OK);
    }
    @GetMapping("budget/bid/{budgetId}")
    @Override
    public ResponseEntity<Response> getBudgetByBudgetId(@PathVariable("budgetId") Integer budgetId) {
        return buildResponse.buildResponse("budget", budgetService.getBudgetByBudgetId(budgetId), "Budget fetched", HttpStatus.OK);
    }
    @GetMapping("budget/user/{userId}")
    @Override
    public ResponseEntity<Response> getUserBudgets(@PathVariable("userId") Integer userId) {
        return buildResponse.buildResponse("budget", budgetService.getUserBudgets(userId), "Budgets fetched", HttpStatus.OK);
    }
    @GetMapping("budget/all")
    @Override
    public ResponseEntity<Response> getAllBudgets() {
        return buildResponse.buildResponse("budget", budgetService.getAllBudgets(), "Budgets fetched", HttpStatus.OK);
    }
    @PutMapping("budget/archive/{budgetId}")
    @Override
    public ResponseEntity<Response> archiveBudget(@PathVariable("budgetId") Integer budgetId) {
        return buildResponse.buildResponse("budget", budgetService.archiveBudget(budgetId), "Budget archived", HttpStatus.OK);
    }
}
