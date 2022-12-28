package com.awesometesting.mapper;

import com.awesometesting.dto.order.CreateOrderRequest;
import com.awesometesting.dto.order.OrderDto;
import com.awesometesting.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Configuration;

@Configuration
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Order toOrder(CreateOrderRequest createOrderRequest);

    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    OrderDto toOrderDto(Order order);
}