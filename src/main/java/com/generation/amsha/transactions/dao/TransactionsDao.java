package com.generation.amsha.transactions.dao;

import com.generation.amsha.transactions.model.Transaction;
import java.util.List;

public interface TransactionsDao {
    Transaction saveTransaction(Transaction transaction);
    Transaction getTransactionById(Integer id);
    List<Transaction> getTransactionsByUserId(Integer userId);
    List<Transaction> getAllTransactions(Integer userId, String startDate, String endDate, String type);
}
