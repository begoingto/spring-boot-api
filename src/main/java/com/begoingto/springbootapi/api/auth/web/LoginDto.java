package com.begoingto.springbootapi.api.auth.web;

import lombok.Builder;

@Builder
public record LoginDto(
        String email,
        String password
) {
}
