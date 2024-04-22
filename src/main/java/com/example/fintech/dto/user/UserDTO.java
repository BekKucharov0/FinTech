package com.example.fintech.dto.user;

import com.example.fintech.dto.card.CardDTO;
import com.example.fintech.enums.UserStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private UserStatus status;

    private List<CardDTO> cards;
}
