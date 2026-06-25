package dev.code925.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.code925.inventory.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
