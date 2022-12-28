package com.awesometesting.dto.order;

import java.time.ZonedDateTime;

public record OrderDto(
        String id,
        String description,
        OrderDto.UserDto user,
        ZonedDateTime createdAt) {

    public record UserDto(String username) {
    }
}