package dev.code925.inventory.services;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.dto.CreateProduct;
import dev.code925.inventory.models.dto.UpdateProductStock;
import dev.code925.inventory.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public String addToInventory(final CreateProduct request) {
        Product product = Product.builder()
                .name(request.getName())
                .stock(0) // Se espesifico iniciar en cero
                .price(request.getPrice())
                .isAvailable(false)
                .build();
        Product saved = productRepository.save(product);
        return String.format("El producto %s con id %d se hacreado.", saved.getName(), saved.getId());
    }

    public Product increaseStock(final String productId, final UpdateProductStock request) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        Integer accumulatedStock = searchProduct.getStock() + request.getStock();
        searchProduct.setStock(accumulatedStock);
        return productRepository.save(searchProduct);
    }

    public Product banned(final String productId) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        searchProduct.setIsAvailable(false);
        return productRepository.save(searchProduct);
    }

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

}
