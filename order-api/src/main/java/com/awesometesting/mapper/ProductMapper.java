package com.awesometesting.mapper;

import com.awesometesting.dto.product.CreateProductRequest;
import com.awesometesting.dto.product.ProductDto;
import com.awesometesting.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Product toProduct(CreateProductRequest createProductRequest);

    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    ProductDto toProductDto(Product saveProduct);

}
