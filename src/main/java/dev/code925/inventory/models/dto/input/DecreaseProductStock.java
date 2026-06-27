package dev.code925.inventory.models.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecreaseProductStock {

    @NotNull(message = "El id del producto es obligatorio.")
    @Positive(message = "El id del producto debe ser positivo.")
    private Long productId;

    @NotNull(message = "El nombre del producto es obligatorio.")
    @Positive(message = "El numero de productos que debe ser un numero positivo.")
    private Integer quantity;

}
