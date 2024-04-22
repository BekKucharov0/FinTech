package com.example.fintech.controller;

import com.example.fintech.dto.user.CreateUserDTO;
import com.example.fintech.dto.user.UpdateUserDTO;
import com.example.fintech.dto.user.UserDTO;
import com.example.fintech.enums.UserStatus;
import com.example.fintech.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable(value = "userId") UUID userId) {

        return userService.getUserById(userId);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {

        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserDTO createUserDTO) {

        return userService.createUser(createUserDTO);
    }

    @PutMapping("/{userId}")
    public UserDTO updateUserDetails(@PathVariable(value = "userId") UUID userId,
                                     @RequestBody UpdateUserDTO updateUserDTO) {

        return userService.updateUserDetails(userId, updateUserDTO);
    }

    @PutMapping("/{userId}/status")
    public UserDTO updateUserStatus(@PathVariable(value = "userId") UUID userId,
                                    @RequestParam(value = "status") UserStatus status) {

        return userService.updateUserStatus(userId, status);
    }

    @DeleteMapping("/{userId}")
    public UserDTO deleteUser(@PathVariable(value = "userId") UUID userId) {

        return userService.deleteUser(userId);
    }
}
