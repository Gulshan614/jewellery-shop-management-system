package com.jewellery.shop.dto;

import com.jewellery.shop.entity.Metal;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "name is required") @Size(max = 120) String name,
        @NotBlank(message = "category is required") @Size(max = 60) String category,
        @NotNull(message = "metal is required") Metal metal,
        @NotNull(message = "weightGrams is required") @DecimalMin(value = "0.001", message = "weightGrams must be greater than 0") BigDecimal weightGrams,
        @NotNull(message = "makingChargePercent is required") @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal makingChargePercent,
        @NotNull(message = "stock is required") @Min(value = 0, message = "stock cannot be negative") Integer stock
) {
}
