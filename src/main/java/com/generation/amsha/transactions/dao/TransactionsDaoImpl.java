package com.generation.amsha.transactions.dao;

import com.generation.amsha.transactions.model.Transaction;
import com.generation.amsha.transactions.model.TransactionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionsDaoImpl implements TransactionsDao{
    private final EntityManager entityManager;
    @Autowired
    public TransactionsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Transaction saveTransaction(Transaction transaction) {
        entityManager.persist(transaction);
        return transaction;
    }

    @Override
    public Transaction getTransactionById(Integer id) {
        TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where id = :id", Transaction.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Integer userId) {
        TypedQuery<Transaction> query = entityManager.createQuery("from Transaction where userAccount.id = :userId", Transaction.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Transaction> getAllTransactions(Integer userId, String startDate, String endDate, String type) {
        // Create CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = cb.createQuery(Transaction.class);
        Root<Transaction> transaction = query.from(Transaction.class);

        // Initialize predicate list
        List<Predicate> predicates = new ArrayList<>();

        // Filter by userId if provided
        if (userId != null) {
            predicates.add(cb.equal(transaction.get("userAccount").get("id"), userId));
        }

        // Filter by startDate if provided
        if (startDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDateTime startDateTime = start.atStartOfDay(); // Start at 00:00:00
            predicates.add(cb.greaterThanOrEqualTo(transaction.get("createdAt"), startDateTime));
        }

        // Filter by endDate if provided
        if (endDate != null) {
            LocalDate end = LocalDate.parse(endDate);
            LocalDateTime endDateTime = end.atTime(23, 59, 59); // End at 23:59:59
            predicates.add(cb.lessThanOrEqualTo(transaction.get("createdAt"), endDateTime));
        }

        // Filter by type if provided
        if (type != null) {
            try {
                TransactionType transactionTypeEnum = TransactionType.valueOf(type);
                predicates.add(cb.equal(transaction.get("transactionType"), transactionTypeEnum));
            } catch (IllegalArgumentException e) {
                // Handle case where the type string does not match any TransactionType enum value
                throw new IllegalArgumentException("Invalid transaction type: " + type);
            }
        }

        // Combine predicates and apply them to the query
        query.select(transaction).where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute query and return result list
        return entityManager.createQuery(query).getResultList();
    }


}
