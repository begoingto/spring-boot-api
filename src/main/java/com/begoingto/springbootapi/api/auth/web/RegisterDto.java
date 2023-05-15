package com.begoingto.springbootapi.api.auth.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegisterDto(
        @NotBlank(message = "The field email is required.")
        @Email
        String email,
        @NotBlank(message = "The field password is required.")
        String password,
        @NotBlank(message = "The field confirm_password is required.")
        String confirmPassword,

        @NotNull(message = "The field roles is required.")
        List<Integer> roleIds
) {
}
