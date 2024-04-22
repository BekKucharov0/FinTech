package com.example.fintech.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    @NotNull(message = "Please insert first name of user")
    private String firstName;

    @NotNull(message = "Please insert last name of user")
    private String lastName;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Please insert date of birth of user")
    private LocalDate dateOfBirth;

    @Email
    @NotNull(message = "Please insert email of user")
    private String email;

    @NotNull(message = "Please insert phone number of user")
    private String phoneNumber;
}
