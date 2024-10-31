package com.generation.amsha.savingsPlan.dao;

import com.generation.amsha.savingsPlan.model.SavingsPlan;
import java.util.List;

public interface SavingsPlanDao {
    SavingsPlan createSavingsPlan(SavingsPlan savingsPlan);
    SavingsPlan updateSavingsPlan(SavingsPlan savingsPlan);
    SavingsPlan getSavingsPlanByPlanId(Integer id);
    List<SavingsPlan> getAllSavingsPlans(Integer userId, String title, String startDate, String endDate);
}
