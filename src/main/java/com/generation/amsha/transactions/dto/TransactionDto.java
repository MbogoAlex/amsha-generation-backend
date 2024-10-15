package com.generation.amsha.transactions.dto;

import com.generation.amsha.transactions.model.TransactionType;
import com.generation.amsha.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDto {
    private Integer id;
    private LocalDateTime createdAt;
    private String mode;
    private Double amount;
    private UserDto user;
    private TransactionType transactionType;
}
