package com.generation.amsha.transactions.service;

import com.generation.amsha.transactions.dto.TransactionDetailsPayload;
import com.generation.amsha.transactions.dto.TransactionDto;
import com.generation.amsha.transactions.dto.TransactionPayload;
import com.generation.amsha.transactions.model.Transaction;
import com.generation.amsha.transactions.model.Wallet;
import org.apache.coyote.BadRequestException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface TransactionsService {
    Map<String, Object> depositMoney(TransactionPayload transaction) throws IOException, URISyntaxException, InterruptedException;

    Boolean checkDepositTransactionStatus(Map<String, Object> ipn) throws URISyntaxException, IOException, InterruptedException;

    TransactionDto getTransactionById(Integer id);
    List<TransactionDto> getTransactionsByUserId(Integer userId);
    List<TransactionDto> getAllTransactions(Integer userId, String startDate, String endDate, String type);
}
