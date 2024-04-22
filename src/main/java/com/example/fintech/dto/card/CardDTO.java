package com.example.fintech.dto.card;

import com.example.fintech.dto.user.UserDTO;
import com.example.fintech.enums.CardStatus;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private UUID id;
    private LocalDate expiryDate;
    private String cvv;
    private String cardNumber;
    private CardStatus status;
    private UUID ownerUserId;
    private UserDTO ownerUser;
}
