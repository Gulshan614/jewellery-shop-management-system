package com.jewellery.shop.dto;

import com.jewellery.shop.entity.Metal;
import com.jewellery.shop.entity.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String category,
        Metal metal,
        BigDecimal weightGrams,
        BigDecimal makingChargePercent,
        Integer stock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getCategory(), p.getMetal(),
                p.getWeightGrams(), p.getMakingChargePercent(), p.getStock(),
                p.getCreatedAt(), p.getUpdatedAt());
    }
}
