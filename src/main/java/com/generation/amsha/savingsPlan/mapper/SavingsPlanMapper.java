package com.generation.amsha.savingsPlan.mapper;

import com.generation.amsha.savingsPlan.dto.SavingsPlanDto;
import com.generation.amsha.savingsPlan.model.SavingsPlan;
import com.generation.amsha.user.mapper.UserAccountMapper;

public class SavingsPlanMapper {
    UserAccountMapper userAccountMapper = new UserAccountMapper();
    public SavingsPlanDto savingsPlanToSavingsPlanDto(SavingsPlan savingsPlan) {
        return SavingsPlanDto.builder()
                .id(savingsPlan.getId())
                .title(savingsPlan.getTitle())
                .description(savingsPlan.getDescription())
                .currentAmount(savingsPlan.getCurrentAmount())
                .targetAmount(savingsPlan.getTargetAmount())
                .createdAt(savingsPlan.getCreatedAt())
                .updatedAt(savingsPlan.getUpdatedAt())
                .archived(savingsPlan.getArchived())
                .user(userAccountMapper.toUserDto(savingsPlan.getUserAccount()))
                .build();

    }
}
