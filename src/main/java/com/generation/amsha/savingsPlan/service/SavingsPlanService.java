package com.generation.amsha.savingsPlan.service;

import com.generation.amsha.savingsPlan.dto.*;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Map;

public interface SavingsPlanService {
    SavingsPlanDto createSavingsPlan(SavingsPlanPayload savingsPlanPayload);
    SavingsPlanDto updateSavingsPlan(SavingsPlanUpdatePayload savingsPlanUpdatePayload);
    SavingsPlanDto archiveSavingsPlan(Integer planId);
    SavingsPlanDto getSavingsPlan(Integer id);
    List<SavingsPlanDto> getAllSavingsPlan(Integer userId, String title, String startDate, String endDate);
    Map<String, Object> depositToSavingsPlan(SavingsPlanDepositDto savingsPlanDepositDto) throws BadRequestException;
    SavingsPlanDto transferToSavingsPlan(SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException;

    SavingsPlanDto transferFromSavingsPlan(SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException;
}
