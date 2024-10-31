package com.generation.amsha.savingsPlan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsPlanTransferDto {
    private Integer userId;
    private Integer planId;
    private Double amount;
}
