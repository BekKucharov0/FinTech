package com.example.fintech.service;

import com.example.fintech.dto.transaction.InitiateTransactionDTO;
import com.example.fintech.dto.transaction.TransactionDTO;
import com.example.fintech.enums.TransactionStatus;
import com.example.fintech.exception.CustomException;
import com.example.fintech.module.Card;
import com.example.fintech.module.Transaction;
import com.example.fintech.repo.TransactionRepo;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final ModelMapper modelMapper;
    private final TransactionRepo transactionRepo;
    private final CardService cardService;

    public List<TransactionDTO> getAllTransactions() {

        List<Transaction> allTransactions = transactionRepo.findAll();
        return allTransactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .toList();
    }

    public TransactionDTO getTransactionById(UUID transactionId) {
        Transaction transaction = transactionRepo.findById(transactionId);
        if (transaction == null) {
            throw new CustomException("Could not find transaction", HttpStatus.BAD_REQUEST);
        }
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    public TransactionDTO initiateTransaction(InitiateTransactionDTO initiateDTO) {

        if (Objects.equals(initiateDTO.getSenderCardId(), initiateDTO.getRecipientCardId())) {
            throw new CustomException("Please check sender and recipient card numbers", HttpStatus.BAD_REQUEST);
        }

        Card senderCard = cardService.getCardById(initiateDTO.getSenderCardId());
        Card recipientCard = cardService.getCardById(initiateDTO.getRecipientCardId());

        Transaction newTransaction = Transaction.builder()
                .amount(initiateDTO.getAmount())
                .transactionType(initiateDTO.getTransactionType())
                .paymentStatus(TransactionStatus.PENDING)
                .description(initiateDTO.getDescription())
                .senderCardId(senderCard.getId())
                .senderCard(senderCard)
                .recipientCardId(recipientCard.getId())
                .recipientCard(recipientCard)
                .senderUserId(senderCard.getOwnerUserId())
                .senderUser(senderCard.getOwnerUser())
                .recipientUserId(recipientCard.getOwnerUserId())
                .recipientUser(recipientCard.getOwnerUser())
                .build();
        Transaction transaction = transactionRepo.save(newTransaction);
        return modelMapper.map(transaction, TransactionDTO.class);
    }
}
