package com.example.fintech.dto.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardDTO {

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Please insert expiry date")
    private LocalDate expiryDate;

    @Size(min = 3, max = 3)
    @NotNull(message = "Please insert cvv")
    private String cvv;

    @Size(min = 16, max = 16)
    @NotNull(message = "Please insert card number")
    private String cardNumber;

    @NotNull(message = "Please insert owner user ID")
    private UUID ownerUserId;
}
