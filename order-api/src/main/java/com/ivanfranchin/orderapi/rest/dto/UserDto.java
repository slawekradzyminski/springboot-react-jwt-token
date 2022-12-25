package com.ivanfranchin.orderapi.rest.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
public record UserDto(
        Long id,
        String username,
        String name,
        String email,
        String role,
        List<OrderDto> orders) {

    public record OrderDto(String id, String description, ZonedDateTime createdAt) {
    }
}