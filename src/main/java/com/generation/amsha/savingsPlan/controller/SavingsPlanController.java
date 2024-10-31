package com.generation.amsha.savingsPlan.controller;

import com.generation.amsha.response.Response;
import com.generation.amsha.savingsPlan.dto.SavingsPlanDepositDto;
import com.generation.amsha.savingsPlan.dto.SavingsPlanPayload;
import com.generation.amsha.savingsPlan.dto.SavingsPlanTransferDto;
import com.generation.amsha.savingsPlan.dto.SavingsPlanUpdatePayload;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public interface SavingsPlanController {
    ResponseEntity<Response> createSavingsPlan(SavingsPlanPayload savingsPlanPayload);
    ResponseEntity<Response> updateSavingsPlan(SavingsPlanUpdatePayload savingsPlanUpdatePayload);
    ResponseEntity<Response> archiveSavingsPlan(Integer planId);
    ResponseEntity<Response> getSavingsPlan(Integer id);
    ResponseEntity<Response> getAllSavingsPlan(Integer userId, String title, String startDate, String endDate);
    ResponseEntity<Response> depositToSavingsPlan(SavingsPlanDepositDto savingsPlanDepositDto) throws BadRequestException;
    ResponseEntity<Response> transferToSavingsPlan(SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException;

    ResponseEntity<Response> transferFromSavingsPlan(SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException;
}
