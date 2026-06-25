package dev.code925.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.code925.inventory.models.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
