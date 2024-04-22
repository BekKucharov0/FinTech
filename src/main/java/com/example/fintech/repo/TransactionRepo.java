package com.example.fintech.repo;

import com.example.fintech.enums.TransactionStatus;
import com.example.fintech.module.Transaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TransactionRepo {

    private static final Map<UUID, Transaction> transactionRepo = new HashMap<>();

    public Transaction findById(UUID transactionId) {
        return transactionRepo.get(transactionId);
    }

    public List<Transaction> findAll() {
        return transactionRepo.values().stream().toList();
    }

    public List<Transaction> findAllByStatus(TransactionStatus status) {
        return transactionRepo.values().stream()
                .filter(transaction -> Objects.equals(transaction.getPaymentStatus(), status))
                .toList();
    }

    public Transaction save(Transaction transaction) {
        UUID transactionId = transaction.getId();
        if (transactionId == null) {
            transactionId = UUID.randomUUID();
            transaction.setId(transactionId);
        }
        transactionRepo.put(transactionId, transaction);
        return transaction;
    }
}
