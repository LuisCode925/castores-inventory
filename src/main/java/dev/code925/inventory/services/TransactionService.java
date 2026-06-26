package dev.code925.inventory.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.code925.inventory.models.Transaction;
import dev.code925.inventory.models.dto.input.CreateTransaction;
import dev.code925.inventory.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction recordMovement(final CreateTransaction request) {
        Transaction transaction = Transaction.builder()
                .product(request.getProduct())
                .quantity(request.getQuantity())
                .responsible(null) // TODO: falta añadir el responsable
                .movement(request.getType())
                .registeredAt(OffsetDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> allTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> filterTransactions(final String name) {
        return transactionRepository.findByMovement(name);
    }

}
