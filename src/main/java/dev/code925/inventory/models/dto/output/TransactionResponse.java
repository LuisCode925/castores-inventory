package dev.code925.inventory.models.dto.output;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long id;

    private String product;

    private Integer quantity;

    private String responsible;

    private String movement;

    private OffsetDateTime registeredAt;

}
