package com.generation.amsha.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWalletDto {
    private Double accountBalance;
    private Double availableBalance;
    private Integer savings;
    private Double savingsAmount;
    private UserDto userDto;
}
