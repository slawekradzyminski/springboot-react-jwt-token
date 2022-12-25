package com.ivanfranchin.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @Schema(example = "iPhone")
    @NotBlank
    private String name;

    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    @Schema(example = "20.55")
    private BigDecimal price;
}
