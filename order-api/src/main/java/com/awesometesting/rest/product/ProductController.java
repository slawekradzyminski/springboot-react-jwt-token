package com.awesometesting.rest.product;

import com.awesometesting.dto.product.CreateProductRequest;
import com.awesometesting.dto.product.ProductDto;
import com.awesometesting.mapper.ProductMapper;
import com.awesometesting.model.Product;
import com.awesometesting.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.awesometesting.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

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
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable UUID id) {
        Product product = productService.getProduct(id).orElseThrow(ProductNotFoundException::new);
        return productMapper.toProductDto(product);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/{id}")
    public ProductDto getProduct(@PathVariable UUID id, @Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.getProduct(id).orElseThrow(ProductNotFoundException::new);
        product.setName(createProductRequest.getName());
        product.setPrice(createProductRequest.getPrice());
        return productMapper.toProductDto(productService.saveProduct(product));
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
