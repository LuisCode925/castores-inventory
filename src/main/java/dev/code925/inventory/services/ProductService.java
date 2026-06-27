package dev.code925.inventory.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.code925.inventory.exceptions.ProductWithoutEnoughtStockException;
import dev.code925.inventory.exceptions.ProductNotFoundException;
import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.dto.input.CreateProduct;
import dev.code925.inventory.models.dto.input.CreateTransaction;
import dev.code925.inventory.models.dto.input.DecreaseProductStock;
import dev.code925.inventory.models.dto.input.IncreaseProductStock;
import dev.code925.inventory.models.enums.TransactionType;
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
        return String.format("El producto %s con id %d se ha creado.", saved.getName(), saved.getId());
    }

    @Transactional
    public Product increaseStock(final String productId, final IncreaseProductStock request, final String token) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId))
                .orElseThrow(() -> new ProductNotFoundException("No existe el producto buscado."));
        Integer accumulatedStock = searchProduct.getStock() + request.getQuantity();
        searchProduct.setStock(accumulatedStock);

        // Registro del movimiento
        CreateTransaction inflowTransaction = CreateTransaction.builder()
                .type(TransactionType.INFLOW)
                .product(searchProduct)
                .quantity(request.getQuantity())
                .build();
        transactionService.recordMovement(inflowTransaction, token);

        return productRepository.save(searchProduct);
    }

    @Transactional
    public Product banned(final String productId) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        searchProduct.setIsAvailable(false);
        return productRepository.save(searchProduct);
    }

    @Transactional
    public Product reactivateProduct(final String productId) {
        Product searchProduct = productRepository.findById(Long.parseLong(productId)).orElseThrow();
        searchProduct.setIsAvailable(true);
        return productRepository.save(searchProduct);
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
    public Product subtractStock(final DecreaseProductStock request, final String token) throws Exception {
        Product searchProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("No existe el producto buscado."));

        if (request.getQuantity() > searchProduct.getStock()) {
            throw new ProductWithoutEnoughtStockException(
                    String.format("No existen suficientes existencias del producto %s con id %d",
                            searchProduct.getName(), searchProduct.getId()));
        }

        Integer currentStock = searchProduct.getStock() - request.getQuantity();
        searchProduct.setStock(currentStock);

        // Registro del movimiento
        CreateTransaction outflowTransaction = CreateTransaction.builder()
                .type(TransactionType.OUTFLOW)
                .product(searchProduct)
                .quantity(request.getQuantity())
                .build();
        transactionService.recordMovement(outflowTransaction, token);

        return productRepository.save(searchProduct);
    }

}
