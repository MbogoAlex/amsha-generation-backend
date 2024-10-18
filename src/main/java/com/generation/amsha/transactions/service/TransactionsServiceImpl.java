package com.generation.amsha.transactions.service;

import com.generation.amsha.transactions.dao.TransactionsDao;
import com.generation.amsha.transactions.dto.TransactionDto;
import com.generation.amsha.transactions.dto.TransactionPayload;
import com.generation.amsha.transactions.mapper.TransactionMapper;
import com.generation.amsha.transactions.model.Transaction;
import com.generation.amsha.transactions.model.TransactionType;
import com.generation.amsha.transactions.model.Wallet;
import com.generation.amsha.user.dao.UserAccountDao;
import com.generation.amsha.user.model.UserAccount;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionsServiceImpl implements TransactionsService{

    TransactionMapper transactionMapper = new TransactionMapper();
    private final TransactionsDao transactionsDao;
    private final UserAccountDao userAccountDao;

    private final String CONSUMER_KEY = "aeMcWKL/TxO/TW6H3E76XCyp3D4RoIGk";
    private final String CONSUMER_SECRET = "rix1TV7Bs0ffJPb/KcFpTh+AVdY=";

    private final String CALLBACK_URL = "https://amsh-a-generation.vercel.app/dashboard";
    private final String notificationId = "3ada2327-9261-4126-9372-dca1ad2a0e3c";
    @Autowired
    public TransactionsServiceImpl(
            TransactionsDao transactionsDao,
            UserAccountDao userAccountDao
    ) {
        this.transactionsDao = transactionsDao;
        this.userAccountDao = userAccountDao;
    }
    @Transactional
    @Override
    public Map<String, Object> depositMoney(TransactionPayload transaction) throws IOException, URISyntaxException, InterruptedException {
        String url = "https://pay.pesapal.com/v3/api/Transactions/SubmitOrderRequest";
        UserAccount userAccount = userAccountDao.getUserById(transaction.getUserId());
        String phoneNumber = transaction.getPhoneNumber();
        String token = generateToken();
        userAccount.setLastPaymentToken(token);
        userAccountDao.updateUser(userAccount);
        String id = UUID.randomUUID().toString();
        userAccount.setLastMerchantReference(id);

        Map<String, Object> billingAddress = new HashMap<>();
        billingAddress.put("email_address", Optional.ofNullable(userAccount.getEmail()).orElse(""));
        billingAddress.put("phone_number", phoneNumber);
        billingAddress.put("country_code", "KE");
        billingAddress.put("first_name", Optional.ofNullable(userAccount.getFullName()).orElse(""));
        billingAddress.put("middle_name", "");
        billingAddress.put("last_name", Optional.ofNullable(userAccount.getFullName()).orElse(""));
        billingAddress.put("line_1", "");
        billingAddress.put("line_2", "");
        billingAddress.put("city", "");
        billingAddress.put("state", "");
        billingAddress.put("postal_code", "");
        billingAddress.put("zip_code", "");

        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("id", id);
        payLoad.put("currency", "KES");
        payLoad.put("amount", transaction.getAmount());
        payLoad.put("description", "Subscription fee");
        payLoad.put("callback_url", CALLBACK_URL);
        payLoad.put("notification_id", notificationId);
        payLoad.put("billing_address", billingAddress);

        System.out.println("NOTIFICATION_ID: "+ notificationId);

        Gson gson = new Gson();



        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+token)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(payLoad)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if (postResponse.statusCode() != HttpStatus.OK.value()) {
            throw new BadRequestException(token+"Failed to initialize payment::: Status code " + postResponse.statusCode() +" response body:: "+ postResponse.body());
        }

        String jsonString = postResponse.body();

        Map<String, Object> paymentResponse = gson.fromJson(jsonString, Map.class);
        paymentResponse.put("token", token);

        return paymentResponse;
    }
    @Transactional
    @Override
    public Boolean checkDepositTransactionStatus(Map<String, Object> ipn) throws URISyntaxException, IOException, InterruptedException {
        UserAccount userAccount = userAccountDao.getUserByMerchantReferenceId((String) ipn.get("OrderMerchantReference"));
        Gson gson = new Gson();

        String token = userAccount.getLastPaymentToken();

        String orderId = (String) ipn.get("OrderTrackingId");

        String url = "https://pay.pesapal.com/v3/api/Transactions/GetTransactionStatus?orderTrackingId="+orderId;

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+token)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        if(getResponse.statusCode() != HttpStatus.OK.value()) {
            return false;
        }

        String jsonString = getResponse.body();

        Map<String, Object> paymentResponse = gson.fromJson(jsonString, Map.class);
        if(paymentResponse.get("status").equals("200") && paymentResponse.get("payment_status_description").equals("Completed")) {
            Wallet wallet = transactionsDao.getWalletById(1);
            Double accountBalance = 0.0;
            if(userAccount.getAccountBalance() != null) {
                accountBalance = userAccount.getAccountBalance();
            }
            wallet.setWalletBalance(wallet.getWalletBalance() + (Double) paymentResponse.get("amount"));
            userAccount.setAccountBalance(accountBalance + (Double) paymentResponse.get("amount"));
            transactionsDao.updateWallet(wallet);
            userAccountDao.updateUser(userAccount);
            return saveTransaction(userAccount.getId(), (Double) paymentResponse.get("amount"));
        } else {
            return false;
        }

    }


    Boolean saveTransaction(Integer userId, Double amount) throws URISyntaxException, IOException, InterruptedException {
        UserAccount userAccount = userAccountDao.getUserById(userId);
        Transaction transaction1 = Transaction.builder()
                .mode("M-PESA")
                .createdAt(LocalDateTime.now().plusHours(3))
                .amount(amount)
                .userAccount(userAccount)
                .transactionType(TransactionType.DEPOSIT)
                .build();
        transactionsDao.saveTransaction(transaction1);

        return true;
    }

    @Override
    public TransactionDto getTransactionById(Integer id) {
        return transactionMapper.transactionToTransactionDto(transactionsDao.getTransactionById(id));
    }

    @Override
    public List<TransactionDto> getTransactionsByUserId(Integer userId) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        List<Transaction> transactions = transactionsDao.getTransactionsByUserId(userId);
        for(Transaction transaction : transactions) {
            transactionDtos.add(transactionMapper.transactionToTransactionDto(transaction));
        }
        return transactionDtos;
    }

    @Override
    public List<TransactionDto> getAllTransactions(Integer userId, String startDate, String endDate, String type) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        List<Transaction> transactions = transactionsDao.getAllTransactions(userId, startDate, endDate, type);
        for(Transaction transaction : transactions) {
            transactionDtos.add(transactionMapper.transactionToTransactionDto(transaction));
        }
        return transactionDtos;
    }

    String generateToken() throws URISyntaxException, IOException, InterruptedException {
        String url = "https://pay.pesapal.com/v3/api/Auth/RequestToken";

        Map<String, Object> getTokenMap = new HashMap<>();
        getTokenMap.put("consumer_key", CONSUMER_KEY);
        getTokenMap.put("consumer_secret", CONSUMER_SECRET);

        Gson gson = new Gson();
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(getTokenMap)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if(postResponse.statusCode() != HttpStatus.OK.value()) {
            throw new BadRequestException("Failed to get token: Status code " + postResponse.statusCode() + "Response body:: "+postResponse.body());
        }

        String jsonString = postResponse.body();
        Map<String, Object> responseMap = gson.fromJson(jsonString, Map.class);

        return (String) responseMap.get("token");
    }
}
