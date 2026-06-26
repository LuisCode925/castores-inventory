package dev.code925.inventory.models.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecreaseProductStock {

    @NotBlank
    @Positive
    private Long productId;

    @NotBlank
    @Positive(message = "El nuemero de productos que desea debe ser positivo.")
    private Integer quantity;

}
