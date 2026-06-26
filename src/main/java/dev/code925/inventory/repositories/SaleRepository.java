package dev.code925.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.code925.inventory.models.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
