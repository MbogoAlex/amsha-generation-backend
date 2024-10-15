package com.generation.amsha.transactions.mapper;

import com.generation.amsha.transactions.dto.TransactionDto;
import com.generation.amsha.transactions.model.Transaction;
import com.generation.amsha.user.mapper.UserAccountMapper;

public class TransactionMapper {
    UserAccountMapper userAccountMapper = new UserAccountMapper();

    public TransactionDto transactionToTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .createdAt(transaction.getCreatedAt())
                .mode(transaction.getMode())
                .amount(transaction.getAmount())
                .user(userAccountMapper.toUserDto(transaction.getUserAccount()))
                .transactionType(transaction.getTransactionType())
                .build();
    }
}
