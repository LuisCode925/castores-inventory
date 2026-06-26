package dev.code925.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.code925.inventory.models.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsAvailableTrue();
}
