package com.generation.amsha.budget.dao;

import com.generation.amsha.budget.model.Budget;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BudgetDaoImpl implements BudgetDao{

    private final EntityManager entityManager;
    @Autowired
    public BudgetDaoImpl(
            EntityManager entityManager
    ) {
        this.entityManager = entityManager;
    }
    @Override
    public Budget saveBudget(Budget budget) {
        entityManager.persist(budget);
        return budget;
    }

    @Override
    public Budget updateBudget(Budget budget) {
        entityManager.merge(budget);
        return budget;
    }

    @Override
    public Budget getBudgetByBudgetId(Integer budgetId) {
        TypedQuery<Budget> query = entityManager.createQuery("from Budget where id = :id", Budget.class);
        query.setParameter("id", budgetId);
        return query.getSingleResult();
    }


    @Override
    public List<Budget> getUserBudgets(Integer userId) {
        TypedQuery<Budget> query = entityManager.createQuery("from Budget where userAccount.id = :id", Budget.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    public List<Budget> getAllBudgets() {
        TypedQuery<Budget> query = entityManager.createQuery("from Budget ", Budget.class);
        return query.getResultList();
    }
}
