package dev.code925.inventory.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseProductStock {

    @NotBlank
    @Positive(message = "La cantidad que solo puede ser un valor positivo.")
    private Integer quantity;

}
