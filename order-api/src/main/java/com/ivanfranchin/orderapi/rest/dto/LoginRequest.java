package com.ivanfranchin.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {

    @Schema(example = "user")
    @Size(min = 3, max = 255, message = "Minimum username length: 3 characters")
    private String username;

    @Schema(example = "user")
    @Size(min = 3, max = 255, message = "Minimum username length: 3 characters")
    private String password;
}
