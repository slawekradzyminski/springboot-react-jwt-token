package com.awesometesting.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
