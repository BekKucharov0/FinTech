package com.example.fintech.dto.user;

import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Email
    private String email;
    private String phoneNumber;
}
