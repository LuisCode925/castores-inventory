package dev.code925.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.code925.inventory.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByMovement(String movement);
}
