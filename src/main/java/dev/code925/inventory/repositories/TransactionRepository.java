package dev.code925.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.code925.inventory.models.Transaction;
import dev.code925.inventory.models.enums.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByMovement(TransactionType movement);
}
