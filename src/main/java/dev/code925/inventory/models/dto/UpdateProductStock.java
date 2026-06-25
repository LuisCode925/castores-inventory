package dev.code925.inventory.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductStock {

    @NotBlank
    @Positive(message = "Solo se puede aumentar el stock del producto.")
    private Integer stock;

}
