package com.example.fintech.module;

import com.example.fintech.enums.TransactionStatus;
import com.example.fintech.enums.TransactionType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private UUID id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus paymentStatus;
    private String description;

    // ManyToOne
    private UUID senderCardId;
    private Card senderCard;

    // ManyToOne
    private UUID recipientCardId;
    private Card recipientCard;

    // ManyToOne
    private UUID senderUserId;
    private User senderUser;

    // ManyToOne
    private UUID recipientUserId;
    private User recipientUser;
}
