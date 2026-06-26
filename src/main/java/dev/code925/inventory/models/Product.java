package dev.code925.inventory.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private String name;

    private Integer stock;

    @Column(precision = 16, scale = 2)
    private BigDecimal price;

    private Boolean isAvailable;

    // Relaciones

    // @OneToMany(mappedBy = "product")
    // private List<Sale> sales;

    // @OneToMany(mappedBy = "product")
    // private List<Transaction> transactions;

}
