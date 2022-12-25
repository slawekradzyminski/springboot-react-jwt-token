package com.ivanfranchin.orderapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class SignUpRequest {

    @Schema(example = "user3")
    @NotBlank
    @Size(min = 3, max = 255, message = "Minimum username length: 3 characters")
    private String username;

    @Schema(example = "user3")
    @Size(min = 3, max = 255, message = "Minimum password length: 3 characters")
    private String password;

    @Schema(example = "User3")
    @NotBlank
    @Size(min = 3, max = 255, message = "Minimum name length: 3 characters")
    private String name;

    @Schema(example = "user3@mycompany.com")
    @Email
    private String email;
}
