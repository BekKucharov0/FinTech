package com.example.fintech.dto.transaction;

import com.example.fintech.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitiateTransactionDTO {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private TransactionType transactionType;

    private String description;

    @NotNull
    private UUID senderCardId;

    @NotNull
    private UUID recipientCardId;
}
