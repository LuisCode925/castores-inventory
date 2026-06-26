package dev.code925.inventory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.code925.inventory.models.Transaction;
import dev.code925.inventory.services.TransactionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<Transaction>> getHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(service.allTransactions());
    }

    @GetMapping("/{type}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String type) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(service.filterTransactions(type.toLowerCase()));
    }

}
