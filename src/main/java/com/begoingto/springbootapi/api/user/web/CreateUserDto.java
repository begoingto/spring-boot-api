package com.begoingto.springbootapi.api.user.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

public record CreateUserDto(
        @NotBlank(message = "The name field is required.")
        String name,
        @NotBlank(message = "The gender field is required.")
        String gender,
        String oneSignalId,
        String studentCardId,

        @NotNull(message = "You have to confirm,are you a student.")
        Boolean isStudent
) {}
