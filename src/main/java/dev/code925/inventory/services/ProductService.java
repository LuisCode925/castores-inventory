package dev.code925.inventory.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.TransactionMovement;
import dev.code925.inventory.models.dto.CreateProduct;
import dev.code925.inventory.models.dto.CreateTransaction;
import dev.code925.inventory.models.dto.DecreaseProductStock;
import dev.code925.inventory.models.dto.IncreaseProductStock;
import dev.code925.inventory.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TransactionService transactionService;

    @Transactional
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

    @Transactional
    public Product increaseStock(final String productId, final IncreaseProductStock request) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        Integer accumulatedStock = searchProduct.getStock() + request.getQuantity();
        searchProduct.setStock(accumulatedStock);

        // Registro del movimiento
        CreateTransaction inflowTransaction = CreateTransaction.builder()
                .type(TransactionMovement.INFLOW)
                .product(searchProduct)
                .quantity(request.getQuantity())
                .build();
        transactionService.recordMovement(inflowTransaction);

        return productRepository.save(searchProduct);
    }

    @Transactional
    public String banned(final String productId) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        searchProduct.setIsAvailable(false);
        Product saved = productRepository.save(searchProduct);

        return String.format("El producto %s con id %d: ha sido desactivado.", saved.getName(), saved.getId());
    }

    @Transactional
    public String reactivateProduct(final String productId) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        searchProduct.setIsAvailable(true);
        Product saved = productRepository.save(searchProduct);

        return String.format("El producto %s con id %d: ha sido activado.", saved.getName(), saved.getId());
    }

    @Transactional(readOnly = true)
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> showOnlyAvailableOnes() {
        return productRepository.findByIsAvailableTrue();
    }

    @Transactional
    public Product subtractStock(final DecreaseProductStock request) throws Exception {
        Product searchProduct = productRepository.findById(request.getProductId()).orElseThrow();

        if (request.getQuantity() > searchProduct.getStock()) {
            throw new Exception(String.format("No existen suficientes existencias del producto %s con id %d",
                    searchProduct.getName(), searchProduct.getId()));
        }

        Integer currentStock = searchProduct.getStock() - request.getQuantity();
        searchProduct.setStock(currentStock);

        // Registro del movimiento
        CreateTransaction outflowTransaction = CreateTransaction.builder()
                .type(TransactionMovement.OUTFLOW)
                .product(searchProduct)
                .quantity(request.getQuantity())
                .build();
        transactionService.recordMovement(outflowTransaction);

        return productRepository.save(searchProduct);
    }

}
