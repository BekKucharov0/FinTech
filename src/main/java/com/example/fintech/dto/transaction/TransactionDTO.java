package com.example.fintech.dto.transaction;

import com.example.fintech.enums.TransactionStatus;
import com.example.fintech.enums.TransactionType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private UUID id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus paymentStatus;
    private String description;

    private TransactionCardDTO senderCard;
    private TransactionCardDTO recipientCard;

    private TransactionUserDTO senderUser;
    private TransactionUserDTO recipientUser;
}
