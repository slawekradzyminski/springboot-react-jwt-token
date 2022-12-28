package com.awesometesting.rest.order;

import com.awesometesting.dto.order.CreateOrderRequest;
import com.awesometesting.dto.order.OrderDto;
import com.awesometesting.mapper.OrderMapper;
import com.awesometesting.model.Order;
import com.awesometesting.model.User;
import com.awesometesting.security.CustomUserDetails;
import com.awesometesting.service.OrderService;
import com.awesometesting.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.awesometesting.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<OrderDto> getOrders(@RequestParam(value = "text", required = false) String text) {
        List<Order> orders = (text == null) ? orderService.getOrders() : orderService.getOrdersContainingText(text);
        return orders.stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto createOrder(@AuthenticationPrincipal CustomUserDetails currentUser,
                                @Valid @RequestBody CreateOrderRequest createOrderRequest) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Order order = orderMapper.toOrder(createOrderRequest);
        order.setId(UUID.randomUUID().toString());
        order.setUser(user);
        return orderMapper.toOrderDto(orderService.saveOrder(order));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public OrderDto deleteOrders(@PathVariable UUID id) {
        Order order = orderService.validateAndGetOrder(id.toString());
        orderService.deleteOrder(order);
        return orderMapper.toOrderDto(order);
    }
}
