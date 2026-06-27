package dev.code925.inventory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.dto.input.CreateProduct;
import dev.code925.inventory.models.dto.input.IncreaseProductStock;
import dev.code925.inventory.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductInflowController {

    private final ProductService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('WAREHOUSEMAN')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.allProducts());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> create(@Valid @RequestBody CreateProduct request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addToInventory(request));
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Product> increaseStock(@Valid @RequestBody IncreaseProductStock request,
            @PathVariable String productId, @RequestHeader("Authorization") String token) {
        Product updated = service.increaseStock(productId, request, token);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{productId}/activate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Product> activateProduct(@PathVariable String productId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.reactivateProduct(productId));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Product> deactivateProduct(@PathVariable String productId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.banned(productId));
    }

}
