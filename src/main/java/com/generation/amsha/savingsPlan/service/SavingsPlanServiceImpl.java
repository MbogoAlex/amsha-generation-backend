package com.generation.amsha.savingsPlan.service;

import com.generation.amsha.savingsPlan.dao.SavingsPlanDao;
import com.generation.amsha.savingsPlan.dto.*;
import com.generation.amsha.savingsPlan.mapper.SavingsPlanMapper;
import com.generation.amsha.savingsPlan.model.SavingsPlan;
import com.generation.amsha.transactions.dto.TransactionPayload;
import com.generation.amsha.transactions.service.TransactionsService;
import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.model.UserAccount;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SavingsPlanServiceImpl implements SavingsPlanService{
    private final SavingsPlanMapper savingsPlanMapper = new SavingsPlanMapper();
    private final SavingsPlanDao savingsPlanDao;
    private final UserAccountDao userAccountDao;
    private final TransactionsService transactionsService;
    @Autowired
    public SavingsPlanServiceImpl(
            SavingsPlanDao savingsPlanDao,
            UserAccountDao userAccountDao,
            TransactionsService transactionsService
    ) {
        this.savingsPlanDao = savingsPlanDao;
        this.userAccountDao = userAccountDao;
        this.transactionsService = transactionsService;
    }
    @Transactional
    @Override
    public SavingsPlanDto createSavingsPlan(SavingsPlanPayload savingsPlanPayload) {
        UserAccount userAccount = userAccountDao.getUserById(savingsPlanPayload.getUserId());
        SavingsPlan savingsPlan = SavingsPlan.builder()
                .title(savingsPlanPayload.getTitle())
                .description(savingsPlanPayload.getDescription())
                .currentAmount(0.0)
                .targetAmount(savingsPlanPayload.getTargetAmount())
                .createdAt(LocalDateTime.now().plusHours(3))
                .userAccount(userAccount)
                .archived(false)
                .build();

        return savingsPlanMapper.savingsPlanToSavingsPlanDto(savingsPlanDao.createSavingsPlan(savingsPlan));
    }
    @Transactional
    @Override
    public SavingsPlanDto updateSavingsPlan(SavingsPlanUpdatePayload savingsPlanUpdatePayload) {
        SavingsPlan savingsPlan = savingsPlanDao.getSavingsPlanByPlanId(savingsPlanUpdatePayload.getPlanId());

        savingsPlan.setTitle(savingsPlanUpdatePayload.getTitle());
        savingsPlan.setDescription(savingsPlanUpdatePayload.getDescription());
        savingsPlan.setUpdatedAt(LocalDateTime.now().plusHours(3));
        savingsPlan.setTargetAmount(savingsPlanUpdatePayload.getTargetAmount());


        return savingsPlanMapper.savingsPlanToSavingsPlanDto(savingsPlanDao.updateSavingsPlan(savingsPlan));
    }
    @Transactional
    @Override
    public SavingsPlanDto archiveSavingsPlan(Integer planId) {
        SavingsPlan savingsPlan = savingsPlanDao.getSavingsPlanByPlanId(planId);
        savingsPlan.setArchived(true);
        return savingsPlanMapper.savingsPlanToSavingsPlanDto(savingsPlanDao.updateSavingsPlan(savingsPlan));
    }

    @Override
    public SavingsPlanDto getSavingsPlan(Integer id) {
        return savingsPlanMapper.savingsPlanToSavingsPlanDto(savingsPlanDao.getSavingsPlanByPlanId(id));
    }

    @Override
    public List<SavingsPlanDto> getAllSavingsPlan(Integer userId, String title, String startDate, String endDate) {
        List<SavingsPlanDto> savingsPlanDtos = new ArrayList<>();
        List<SavingsPlan> savingsPlans = savingsPlanDao.getAllSavingsPlans(userId, title, startDate, endDate);

        for(SavingsPlan savingsPlan : savingsPlans) {
            savingsPlanDtos.add(savingsPlanMapper.savingsPlanToSavingsPlanDto(savingsPlan));
        }

        return savingsPlanDtos;
    }
    @Transactional
    @Override
    public Map<String, Object> depositToSavingsPlan(SavingsPlanDepositDto savingsPlanDepositDto) throws BadRequestException {
        SavingsPlan savingsPlan = savingsPlanDao.getSavingsPlanByPlanId(savingsPlanDepositDto.getPlanId());
        TransactionPayload transactionPayload = TransactionPayload.builder()
                .userId(savingsPlan.getUserAccount().getId())
                .phoneNumber(savingsPlan.getUserAccount().getPhoneNumber())
                .amount(savingsPlanDepositDto.getAmount())
                .build();
        try {
            SavingsPlan savingsPlan1 = savingsPlanDao.getSavingsPlanByPlanId(savingsPlanDepositDto.getPlanId());
            UserAccount userAccount = savingsPlan1.getUserAccount();
            userAccount.setDepositToSavingsPlan(true);
            userAccount.setPlanId(savingsPlanDepositDto.getPlanId());
            userAccountDao.updateUser(userAccount);
            return transactionsService.depositMoney(transactionPayload);
        } catch (Exception e) {
            throw new BadRequestException("Failed to deposit money");
        }
    }
    @Transactional
    @Override
    public SavingsPlanDto transferToSavingsPlan(SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException {
        SavingsPlan savingsPlan = savingsPlanDao.getSavingsPlanByPlanId(savingsPlanTransferDto.getPlanId());
        UserAccount userAccount = userAccountDao.getUserById(savingsPlanTransferDto.getUserId());

        if(userAccount.getAccountBalance() >= savingsPlanTransferDto.getAmount()) {
            Double currentSavingsAmount = savingsPlan.getCurrentAmount();
            savingsPlan.setCurrentAmount(currentSavingsAmount + savingsPlanTransferDto.getAmount());
            return savingsPlanMapper.savingsPlanToSavingsPlanDto(savingsPlanDao.updateSavingsPlan(savingsPlan));
        } else {
            throw new BadRequestException("Failed to transferToSavingsPlan");
        }
    }
    @Transactional
    @Override
    public SavingsPlanDto transferFromSavingsPlan(SavingsPlanTransferDto savingsPlanTransferDto) throws BadRequestException {
        SavingsPlan savingsPlan = savingsPlanDao.getSavingsPlanByPlanId(savingsPlanTransferDto.getPlanId());
        UserAccount userAccount = userAccountDao.getUserById(savingsPlanTransferDto.getUserId());

        if(savingsPlan.getCurrentAmount() >= savingsPlanTransferDto.getAmount()) {
            Double currentSavingsAmount = savingsPlan.getCurrentAmount();
            savingsPlan.setCurrentAmount(currentSavingsAmount - savingsPlanTransferDto.getAmount());
            return savingsPlanMapper.savingsPlanToSavingsPlanDto(savingsPlanDao.updateSavingsPlan(savingsPlan));
        } else {
            throw new BadRequestException("Failed to transferToSavingsPlan");
        }
    }
}
