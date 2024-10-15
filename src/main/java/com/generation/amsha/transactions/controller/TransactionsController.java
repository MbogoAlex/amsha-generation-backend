package com.generation.amsha.transactions.controller;

import com.generation.amsha.response.Response;
import com.generation.amsha.transactions.dto.TransactionDto;
import com.generation.amsha.transactions.dto.TransactionPayload;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface TransactionsController {
    ResponseEntity<Response> depositMoney(TransactionPayload transaction) throws IOException, URISyntaxException, InterruptedException;
    ResponseEntity<Response> getTransactionById(Integer id);
    ResponseEntity<Response> getTransactionsByUserId(Integer userId);
    ResponseEntity<Response> getAllTransactions(Integer userId, String startDate, String endDate, String type);

    ResponseEntity<Response> getInstantPaymentNotification(Map<String, Object> ipn) throws URISyntaxException, IOException, InterruptedException;
}
