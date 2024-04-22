package com.example.fintech.controller;

import com.example.fintech.dto.transaction.InitiateTransactionDTO;
import com.example.fintech.dto.transaction.TransactionDTO;
import com.example.fintech.service.TransactionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/all")
    public List<TransactionDTO> getAllTransactions() {

        return transactionService.getAllTransactions();
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO getTransactionById(@PathVariable(value = "transactionId") UUID transactionId) {

        return transactionService.getTransactionById(transactionId);
    }

    @PostMapping
    public TransactionDTO initiateTransaction(@RequestBody InitiateTransactionDTO initiateTransactionDTO) {

        return transactionService.initiateTransaction(initiateTransactionDTO);
    }
}
