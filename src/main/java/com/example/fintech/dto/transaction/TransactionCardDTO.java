package com.example.fintech.dto.transaction;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCardDTO {

    private UUID id;
    private String cvv;
    private String cardNumber;
}
