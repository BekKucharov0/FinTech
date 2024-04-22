package com.example.fintech.module;

import com.example.fintech.enums.CardStatus;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    private UUID id;
    private LocalDate expiryDate;
    private String cvv;
    private String cardNumber;
    private CardStatus status;

    // ManyToOne
    private UUID ownerUserId;
    private User ownerUser;

}
