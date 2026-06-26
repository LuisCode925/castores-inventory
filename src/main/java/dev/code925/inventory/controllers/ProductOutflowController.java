package dev.code925.inventory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.dto.input.DecreaseProductStock;
import dev.code925.inventory.services.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductOutflowController {

    private final ProductService service;

    @GetMapping("/actives")
    @PreAuthorize("hasRole('WAREHOUSEMAN')") // La funcion hasRole Añade ROLE_
    public ResponseEntity<List<Product>> getActiveProducts() {
        return ResponseEntity.ok(service.showOnlyAvailableOnes());
    }

    @PatchMapping("/outflow")
    @PreAuthorize("hasRole('WAREHOUSEMAN')")
    public ResponseEntity<Product> decreaseStock(@RequestBody DecreaseProductStock request,
            @RequestHeader("Authorization") String token) throws Exception {
        Product updated = service.subtractStock(request, token);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

}
