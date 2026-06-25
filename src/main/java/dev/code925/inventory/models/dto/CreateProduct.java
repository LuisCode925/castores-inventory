package dev.code925.inventory.models.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {

    @NotBlank
    @Length(max = 40)
    private String name;

    @NotBlank
    @Positive
    private BigDecimal price;

}
