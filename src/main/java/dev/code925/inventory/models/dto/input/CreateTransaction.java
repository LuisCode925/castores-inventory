package dev.code925.inventory.models.dto.input;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransaction {

    private TransactionType type;

    private Product product;

    private Integer quantity;

}
