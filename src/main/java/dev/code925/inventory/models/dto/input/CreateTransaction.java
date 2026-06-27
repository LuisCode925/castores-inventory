package dev.code925.inventory.models.dto.input;

import dev.code925.inventory.models.Product;
import dev.code925.inventory.models.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private TransactionType type;

    @NotBlank
    private Product product;

    @NotBlank
    @Positive
    private Integer quantity;

}
