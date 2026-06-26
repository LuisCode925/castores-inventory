package dev.code925.inventory.models.dto.input;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.TransactionMovement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransaction {

    private TransactionMovement type;

    private Product product;

    private Integer quantity;

}
