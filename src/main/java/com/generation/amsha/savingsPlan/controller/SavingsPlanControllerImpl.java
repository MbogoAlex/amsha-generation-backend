package com.generation.amsha.savingsPlan.controller;

import com.generation.amsha.response.BuildResponse;
import com.generation.amsha.response.Response;
import com.generation.amsha.savingsPlan.dto.SavingsPlanDepositDto;
import com.generation.amsha.savingsPlan.dto.SavingsPlanPayload;
import com.generation.amsha.savingsPlan.dto.SavingsPlanTransferDto;
import com.generation.amsha.savingsPlan.dto.SavingsPlanUpdatePayload;
import com.generation.amsha.savingsPlan.service.SavingsPlanService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class SavingsPlanControllerImpl implements SavingsPlanController {
    private final BuildResponse buildResponse = new BuildResponse();
    private final SavingsPlanService savingsPlanService;
    @Autowired
    public SavingsPlanControllerImpl(
            SavingsPlanService savingsPlanService
    ) {
        this.savingsPlanService = savingsPlanService;
    }
    @PostMapping("savings")
    @Override
    public ResponseEntity<Response> createSavingsPlan(@RequestBody SavingsPlanPayload savingsPlanPayload) {
        System.out.println("CREATING SAVINGS: "+savingsPlanPayload);
        return buildResponse.buildResponse("savings", savingsPlanService.createSavingsPlan(savingsPlanPayload), "Savings plan created", HttpStatus.CREATED);
    }
    @PutMapping("savings")
    @Override
    public ResponseEntity<Response> updateSavingsPlan(@RequestBody SavingsPlanUpdatePayload savingsPlanUpdatePayload) {
        return buildResponse.buildResponse("savings", savingsPlanService.updateSavingsPlan(savingsPlanUpdatePayload), "Savings plan updated", HttpStatus.OK);
    }
    @PutMapping("savings/archive/{planId}")
    @Override
    public ResponseEntity<Response> archiveSavingsPlan(@PathVariable("planId") Integer planId) {
        return buildResponse.buildResponse("savings", savingsPlanService.archiveSavingsPlan(planId), "Savings plan archived", HttpStatus.OK);
    }
    @GetMapping("savings/pid/{planId}")
    @Override
    public ResponseEntity<Response> getSavingsPlan(@PathVariable("planId") Integer id) {
        return buildResponse.buildResponse("savings", savingsPlanService.getSavingsPlan(id), "Savings plan fetched", HttpStatus.OK);
    }
    @GetMapping("savings")
    @Override
    public ResponseEntity<Response> getAllSavingsPlan(
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
    ) {
        return buildResponse.buildResponse("savings", savingsPlanService.getAllSavingsPlan(userId, title, startDate, endDate), "Savings plan fetched", HttpStatus.OK);
    }
    @PutMapping("savings/deposit")
    @Override
    public ResponseEntity<Response> depositToSavingsPlan(@RequestBody SavingsPlanDepositDto savingsPlanDepositDto) throws BadRequestException {
        return buildResponse.buildResponse("savings", savingsPlanService.depositToSavingsPlan(savingsPlanDepositDto), "Deposited to savings plan", HttpStatus.OK);
    }
    @PutMapping("savings/transferto")
    @Override
    public ResponseEntity<Response> transferToSavingsPlan(@RequestBody SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException {
        return buildResponse.buildResponse("savings", savingsPlanService.transferToSavingsPlan(savingsPlanTransferDto), "Transferred to savings plan", HttpStatus.OK);
    }
    @PutMapping("savings/transferfrom")
    @Override
    public ResponseEntity<Response> transferFromSavingsPlan(@RequestBody SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException {
        return buildResponse.buildResponse("savings", savingsPlanService.transferFromSavingsPlan(savingsPlanTransferDto), "Transferred from savings plan", HttpStatus.OK);
    }
}
