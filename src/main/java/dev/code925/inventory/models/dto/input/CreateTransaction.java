package dev.code925.inventory.models.dto.input;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransaction {

    @NotNull(message = "El tipo de transaccion es obligatorio.")
    private TransactionType type;

    @NotNull(message = "El producto es un parametro obligatorio")
    private Product product;

    @NotNull(message = "La cantidad es un parametro obligatorio")
    @Positive(message = "La cantidad debe ser un numero positivo.")
    private Integer quantity;

}
