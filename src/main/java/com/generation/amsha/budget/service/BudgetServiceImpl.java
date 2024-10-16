package com.generation.amsha.budget.service;

import com.generation.amsha.budget.dao.BudgetDao;
import com.generation.amsha.budget.dto.BudgetDto;
import com.generation.amsha.budget.dto.BudgetPayload;
import com.generation.amsha.budget.dto.BudgetUpdatePayload;
import com.generation.amsha.budget.mapper.BudgetMapper;
import com.generation.amsha.budget.model.Budget;
import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.model.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService{

    BudgetMapper budgetMapper = new BudgetMapper();
    private final BudgetDao budgetDao;
    private final UserAccountDao userAccountDao;
    @Autowired
    public BudgetServiceImpl(
            BudgetDao budgetDao,
            UserAccountDao userAccountDao
    ) {
        this.budgetDao = budgetDao;
        this.userAccountDao = userAccountDao;
    }
    @Transactional
    @Override
    public BudgetDto saveBudget(BudgetPayload budgetPayload) {
        UserAccount userAccount = userAccountDao.getUserById(budgetPayload.getUserId());
        Budget budget = Budget.builder()
                .name(budgetPayload.getName())
                .plannedAmount(budgetPayload.getPlannedAmount())
                .actualAmount(budgetPayload.getActualAmount())
                .userAccount(userAccount)
                .archived(false)
                .createdAt(LocalDateTime.now().plusHours(3))
                .updatedAt(LocalDateTime.now().plusHours(3))
                .build();

        return budgetMapper.budgetToBudgetDto(budgetDao.saveBudget(budget));
    }
    @Transactional
    @Override
    public BudgetDto updateBudget(BudgetUpdatePayload budgetPayload) {
        Budget budget = budgetDao.getBudgetByBudgetId(budgetPayload.getBudgetId());
        budget.setName(budgetPayload.getName());
        budget.setPlannedAmount(budgetPayload.getPlannedAmount());
        budget.setActualAmount(budgetPayload.getActualAmount());
        budget.setUpdatedAt(LocalDateTime.now().plusHours(3));

        return budgetMapper.budgetToBudgetDto(budgetDao.updateBudget(budget));
    }

    @Override
    public BudgetDto getBudgetByBudgetId(Integer budgetId) {
        return budgetMapper.budgetToBudgetDto(budgetDao.getBudgetByBudgetId(budgetId));
    }

    @Override
    public List<BudgetDto> getUserBudgets(Integer userId) {
        List<BudgetDto> budgetDtos = new ArrayList<>();
        List<Budget> budgets = budgetDao.getUserBudgets(userId);

        for(Budget budget : budgets) {
            budgetDtos.add(budgetMapper.budgetToBudgetDto(budget));
        }
        return budgetDtos;
    }

    @Override
    public List<BudgetDto> getAllBudgets() {
        List<BudgetDto> budgetDtos = new ArrayList<>();
        List<Budget> budgets = budgetDao.getAllBudgets();

        for(Budget budget : budgets) {
            budgetDtos.add(budgetMapper.budgetToBudgetDto(budget));
        }
        return budgetDtos;
    }
    @Transactional
    @Override
    public String archiveBudget(Integer budgetId) {
        Budget budget = budgetDao.getBudgetByBudgetId(budgetId);
        budget.setArchived(true);
        return "Budget archived";
    }
}
