package com.awesometesting.dto.product;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record ProductDto(
        String id,
        String name,
        BigDecimal price,
        ZonedDateTime createdAt) {
}