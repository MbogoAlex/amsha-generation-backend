package com.generation.amsha.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionPayload {
    private Integer userId;
    private String phoneNumber;
    private String amount;
}
