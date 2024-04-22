package com.example.fintech.dto.transaction;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionUserDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
