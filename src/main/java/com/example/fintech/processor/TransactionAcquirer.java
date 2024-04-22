package com.example.fintech.processor;

import com.example.fintech.enums.TransactionStatus;
import com.example.fintech.module.Card;
import com.example.fintech.module.Transaction;
import com.example.fintech.repo.TransactionRepo;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionAcquirer {

    private final TransactionRepo transactionRepo;

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.MINUTES)
    public void processTransactions() {

        System.out.println("Started processing transactions");
        List<Transaction> pendingTransactions = transactionRepo.findAllByStatus(TransactionStatus.PENDING);

        for (Transaction transaction : pendingTransactions) {

            Card senderCard = transaction.getSenderCard();
            String cardNumber = senderCard.getCardNumber();
            int lastDigit = Integer.parseInt(cardNumber.substring(cardNumber.length() - 1));
            boolean evenDigit = lastDigit % 2 == 0;

            if (evenDigit) {
                transaction.setPaymentStatus(TransactionStatus.SETTLED);
            } else {
                transaction.setPaymentStatus(TransactionStatus.DECLINED);
            }
            transaction.setDescription(transaction.getDescription() + " | Processed");
            transactionRepo.save(transaction);
        }
    }
}
