package com.generation.amsha.transactions.dao;

import com.generation.amsha.transactions.model.Transaction;
import com.generation.amsha.transactions.model.Wallet;

import java.util.List;

public interface TransactionsDao {
    Transaction saveTransaction(Transaction transaction);
    Wallet updateWallet(Wallet wallet);
    Wallet getWalletById(Integer id);
    Transaction getTransactionById(Integer id);
    List<Transaction> getTransactionsByUserId(Integer userId);
    List<Transaction> getAllTransactions(Integer userId, String startDate, String endDate, String type);
}
