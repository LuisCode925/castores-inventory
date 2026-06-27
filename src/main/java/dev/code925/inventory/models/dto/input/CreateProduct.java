package dev.code925.inventory.models.dto.input;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {

    @NotBlank(message = "El nombre del producto es obligatorio.")
    @Length(max = 40, message = "El nombre del producto debe terner maximo 40 caracteres.")
    private String name;

    @NotNull(message = "El precio del producto es obligatorio.")
    @Positive(message = "El precio debe ser un numero positivo.")
    private BigDecimal price;

}
