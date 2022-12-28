package com.ivanfranchin.orderapi.rest.product;

import com.ivanfranchin.orderapi.dto.product.CreateProductRequest;
import com.ivanfranchin.orderapi.dto.product.ProductDto;
import com.ivanfranchin.orderapi.mapper.ProductMapper;
import com.ivanfranchin.orderapi.model.Product;
import com.ivanfranchin.orderapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.ivanfranchin.orderapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getAll().stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = productMapper.toProduct(createProductRequest);
        product.setId(UUID.randomUUID().toString());
        return productMapper.toProductDto(productService.saveProduct(product));
    }

}
