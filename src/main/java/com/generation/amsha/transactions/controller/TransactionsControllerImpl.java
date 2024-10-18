package com.generation.amsha.transactions.controller;

import com.generation.amsha.response.BuildResponse;
import com.generation.amsha.response.Response;
import com.generation.amsha.transactions.dto.TransactionDto;
import com.generation.amsha.transactions.dto.TransactionPayload;
import com.generation.amsha.transactions.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api/")
public class TransactionsControllerImpl implements TransactionsController{
    BuildResponse buildResponse = new BuildResponse();
    private final TransactionsService transactionsService;
    @Autowired
    public TransactionsControllerImpl(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }
    @PostMapping("transactions/deposit")
    @Override
    public ResponseEntity<Response> depositMoney(@RequestBody TransactionPayload transaction) throws IOException, URISyntaxException, InterruptedException {
        return buildResponse.buildResponse("transaction", transactionsService.depositMoney(transaction), "Operation complete", HttpStatus.OK);
    }
    @GetMapping("transactions/tid/{id}")
    @Override
    public ResponseEntity<Response> getTransactionById(@PathVariable("id") Integer id) {
        return buildResponse.buildResponse("transaction", transactionsService.getTransactionById(id), "Transaction fetched", HttpStatus.OK);
    }
    @GetMapping("transactions/uid/{userId}")
    @Override
    public ResponseEntity<Response> getTransactionsByUserId(@PathVariable("userId") Integer userId) {
        return buildResponse.buildResponse("transaction", transactionsService.getTransactionsByUserId(userId), "Transactions fetched", HttpStatus.OK);
    }
    @GetMapping("transactions/all")
    @Override
    public ResponseEntity<Response> getAllTransactions(
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            String type) {
        return buildResponse.buildResponse("transaction", transactionsService.getAllTransactions(userId, startDate, endDate, type), "Transactions fetched", HttpStatus.OK);
    }
    @PostMapping("transaction/ipn")
    @Override
    public ResponseEntity<Response> getInstantPaymentNotification(@RequestBody Map<String, Object> ipn) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("IPN");
        System.out.println(ipn);
        System.out.println();
        return buildResponse.buildResponse("transaction", transactionsService.checkDepositTransactionStatus(ipn), "IPN received", HttpStatus.OK);
    }
}
