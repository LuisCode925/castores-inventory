package dev.code925.inventory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.dto.CreateProduct;
import dev.code925.inventory.models.dto.UpdateProductStock;
import dev.code925.inventory.services.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateProduct request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addToInventory(request));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> increaseStock(@RequestBody UpdateProductStock request,
            @PathVariable String productId) {
        Product updated = service.increaseStock(productId, request);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{productId}/deactivate")
    public ResponseEntity<Product> deactivateProduct(@PathVariable String productId) {
        Product updated = service.banned(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllProducts() {
        return ResponseEntity.ok(service.allProducts());
    }

    /*
     * 
     * @GetMapping("/{id}")
     * public ResponseEntity<?> getCustomer(@PathVariable String customerId) {
     * return ResponseEntity.ok(customerService.getCustomerById(customerId));
     * }
     * 
     * @PutMapping("/{id}")
     * public ResponseEntity<Customer> fullUpdate(@Valid @RequestBody UpdateCustomer
     * request,
     * 
     * @PathVariable String customerId) {
     * Customer updated = customerService.update(customerId, request);
     * return ResponseEntity.status(HttpStatus.OK).body(updated);
     * }
     * 
     * @DeleteMapping("/{customerId}")
     * public ResponseEntity<Void> delete(@PathVariable("customerId") String
     * customerId) {
     * customerService.delete(customerId);
     * return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
     * }
     */

}
