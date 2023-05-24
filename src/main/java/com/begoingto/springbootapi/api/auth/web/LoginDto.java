package com.begoingto.springbootapi.api.auth.web;

import com.begoingto.springbootapi.api.user.validator.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDto(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Password
        String password
) {
}
