package com.example.fintech.service;

import com.example.fintech.dto.user.CreateUserDTO;
import com.example.fintech.dto.user.UpdateUserDTO;
import com.example.fintech.dto.user.UserDTO;
import com.example.fintech.enums.UserStatus;
import com.example.fintech.exception.CustomException;
import com.example.fintech.module.User;
import com.example.fintech.repo.UserRepo;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

    public UserDTO getUserById(UUID userId) {
        User user = userRepo.findById(userId);
        if (user == null) {
            throw new CustomException("Could not find a user", HttpStatus.BAD_REQUEST);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    public UserDTO createUser(CreateUserDTO createDTO) {

        LocalDate dateOfBirth = createDTO.getDateOfBirth();
        int userAge = LocalDate.now().getYear() - dateOfBirth.getYear();
        if (userAge < 18) {
            throw new CustomException("User should be older than 18", HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOpt = userRepo.findByEmail(createDTO.getEmail());
        if (userOpt.isPresent()) {
            throw new CustomException("User with the same email already exists", HttpStatus.BAD_REQUEST);
        }

        User newUser = User.builder()
                .firstName(createDTO.getFirstName())
                .lastName(createDTO.getLastName())
                .dateOfBirth(dateOfBirth)
                .email(createDTO.getEmail())
                .phoneNumber(createDTO.getPhoneNumber())
                .status(UserStatus.ACTIVE)
                .build();

        User user = userRepo.save(newUser);
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO updateUserDetails(UUID userId, UpdateUserDTO updateDTO) {

        User userToUpdate = userRepo.findById(userId);
        if (userToUpdate == null) {
            throw new CustomException("Could not find user", HttpStatus.BAD_REQUEST);
        }

        if (updateDTO.getDateOfBirth() != null && !Objects.equals(userToUpdate.getDateOfBirth(), updateDTO.getDateOfBirth())) {
            LocalDate dateOfBirth = updateDTO.getDateOfBirth();
            int userAge = LocalDate.now().getYear() - dateOfBirth.getYear();
            if (userAge >= 18) {
                throw new CustomException("User should be older than 18", HttpStatus.BAD_REQUEST);
            }
        }

        if (updateDTO.getEmail() != null && !Objects.equals(userToUpdate.getEmail(), updateDTO.getEmail())) {
            Optional<User> userOpt = userRepo.findByEmail(updateDTO.getEmail());
            if (userOpt.isPresent()) {
                throw new CustomException("User with the same email already exists", HttpStatus.BAD_REQUEST);
            }
        }

        userToUpdate.setFirstName(updateDTO.getFirstName() != null ? updateDTO.getFirstName() : userToUpdate.getFirstName());
        userToUpdate.setLastName(updateDTO.getLastName() != null ? updateDTO.getLastName() : userToUpdate.getLastName());
        userToUpdate.setDateOfBirth(updateDTO.getDateOfBirth() != null ? updateDTO.getDateOfBirth() : userToUpdate.getDateOfBirth());
        userToUpdate.setEmail(updateDTO.getEmail() != null ? updateDTO.getEmail() : userToUpdate.getEmail());
        userToUpdate.setPhoneNumber(updateDTO.getPhoneNumber() != null ? updateDTO.getPhoneNumber() : userToUpdate.getPhoneNumber());
        User updatedUser = userRepo.save(userToUpdate);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public UserDTO updateUserStatus(UUID userId, UserStatus status) {

        User userToUpdate = userRepo.findById(userId);
        if (userToUpdate == null) {
            throw new CustomException("Could not find user", HttpStatus.BAD_REQUEST);
        }

        if (Objects.equals(userToUpdate.getStatus(), status)) {
            return modelMapper.map(userToUpdate, UserDTO.class);
        }

        userToUpdate.setStatus(status);
        User updatedUser = userRepo.save(userToUpdate);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public UserDTO deleteUser(UUID userId) {

        User userToDelete = userRepo.findById(userId);
        if (userToDelete == null) {
            throw new CustomException("Could not find user", HttpStatus.BAD_REQUEST);
        }

        User deletedUser = userRepo.delete(userToDelete);
        return modelMapper.map(deletedUser, UserDTO.class);
    }
}
