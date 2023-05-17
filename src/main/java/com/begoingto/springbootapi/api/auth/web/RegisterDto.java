package com.begoingto.springbootapi.api.auth.web;

import com.begoingto.springbootapi.api.user.validator.email.EmailUnique;
import com.begoingto.springbootapi.api.user.validator.password.Password;
import com.begoingto.springbootapi.api.user.validator.role.RoleIdConstraint;
import jakarta.validation.constraints.*;

import java.util.List;

public record RegisterDto(
        @NotBlank(message = "The field email is required.")
        @EmailUnique
        @Email
        @Size(max = 254)
        String email,
        @NotBlank(message = "The field password is required.")
        @Password
//        @Size(min = 6, max = 20)
        String password,
        @NotBlank(message = "The field confirm_password is required.")
        @Password
//        @Size(min = 6, max = 20)
        String confirmPassword,

        @NotNull(message = "The field roles is required.")
        @RoleIdConstraint
        List<Integer> roleIds
) {
}
