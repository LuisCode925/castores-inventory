package dev.code925.inventory.models.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseProductStock {

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad que solo puede ser un valor positivo.")
    private Integer quantity;

}
