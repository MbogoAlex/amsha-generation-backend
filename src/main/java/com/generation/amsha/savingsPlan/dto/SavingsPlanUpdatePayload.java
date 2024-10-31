package com.generation.amsha.savingsPlan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsPlanUpdatePayload {
    private Integer planId;
    private String title;
    private String description;
    private Double targetAmount;
    private Integer userId;
}
